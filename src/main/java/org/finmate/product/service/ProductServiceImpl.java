package org.finmate.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.adapter.openai.OpenAiApi;
import org.finmate.adapter.openai.dto.OpenAiResponseDTO;
import org.finmate.common.util.PromptLoader;
import org.finmate.exception.NotFoundException;
import org.finmate.member.domain.AnimalCharacterVO;
import org.finmate.member.domain.CustomUser;
import org.finmate.member.domain.UserInfoVO;
import org.finmate.member.dto.UserInfoDTO;
import org.finmate.member.mapper.AnimalCharacterMapper;
import org.finmate.member.mapper.UserInfoMapper;
import org.finmate.member.mapper.UserMapper;
import org.finmate.portfolio.domain.InvestmentProfile;
import org.finmate.portfolio.domain.PortfolioVO;
import org.finmate.portfolio.dto.PortfolioDTO;
import org.finmate.portfolio.mapper.PortfolioMapper;
import org.finmate.product.domain.DepositVO;
import org.finmate.product.domain.FundVO;
import org.finmate.product.domain.ProductReviewVO;
import org.finmate.product.domain.SavingsVO;
import org.finmate.product.dto.ProductComparisonResultDTO;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.dto.ProductFilterDTO;
import org.finmate.product.dto.ProductReviewDTO;
import org.finmate.product.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;

    private final UserInfoMapper userInfoMapper;

    private final PortfolioMapper portfolioMapper;

    private final AnimalCharacterMapper animalCharacterMapper;

    private final OpenAiApi openAiApi;
    //TODO: 필터링 서비스 구현

    @Override
    public List<ProductDTO<?>> getAllProducts() {
        return productMapper.getAllProducts()
                .stream()
                .map(ProductDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO<?> getProductDetail(Long id) {
        return ProductDTO.from(
                Optional.ofNullable(productMapper.getProductDetail(id))
                        .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."))
        );
    }

    @Override
    public ProductComparisonResultDTO compareProducts(Long id1, Long id2, CustomUser user) {
        ProductDTO<?> data1 = getProductDetail(id1);
        ProductDTO<?> data2 = getProductDetail(id2);

        //비교 프롬프트 텍스트 로드
        String text = PromptLoader.load("/prompts/product_compare.txt");


        //TODO: 나머지 5개 수치 추천 알고리즘 점수로 추가로 넣으면 좋을듯
        String userData = "";
        String tone = "귀여운 키위새";
        if(user != null) {
            Long userId = user.getUser().getId();
            UserInfoVO userInfo = userInfoMapper.getUserInfoById(userId);
            PortfolioVO userPortfolio = portfolioMapper.getPortfolio(userId);
            Long animalId = userInfo.getAnimalId();
            AnimalCharacterVO animalCharacter = animalCharacterMapper.getCharacterById(animalId);
            tone = userInfo.getProfileSummary() + animalCharacter.getAnimalName();
            userData += userInfo.toString();
            userData += userPortfolio.toString();
        }
        text = text.formatted(tone, userData, data1.toVO().toString(), data2.toVO().toString());

        log.info(text);
        OpenAiResponseDTO response = openAiApi.callResponses(text);

        String comparisonResult = response.getOutput().get(0).getContent().get(0).getText();

        ProductComparisonResultDTO result = ProductComparisonResultDTO.builder()
                .product1(data1)
                .product2(data2)
                .comparisonResult(comparisonResult)
                .build();

        return result;
    }

    @Override
    public List<ProductReviewDTO> getProductReviews(Long id) {
        return productMapper.getProductReviewByProductId(id)
                .stream()
                .map(ProductReviewDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReviewDTO> getMyReviews(Long userId) {
        return productMapper.getProductReviewByUserId(userId)
                .stream()
                .map(ProductReviewDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReviewDTO> getMyReviewsByType(Long userId, String productType) {
        return productMapper.getProductReviewByUserIdAndType(userId, productType)
                .stream()
                .map(ProductReviewDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public Long insertProductReview(ProductReviewDTO productReviewDTO, Long productId, Long userId) {
        ProductReviewVO existingReview = productMapper.getProductReviewByProductIdAndUserId(productId, userId);
        if (existingReview != null) {
            throw new NotFoundException("이미 등록된 리뷰가 있습니다.");
        }

        UserInfoVO userInfo = userInfoMapper.getUserInfoById(userId);
        Long animalId = userInfo.getAnimalId();
        AnimalCharacterVO animalCharacter = animalCharacterMapper.getCharacterById(userId);

        String writer = userInfo.getProfileSummary();
        if (animalCharacter != null) {
            writer += " " + animalCharacter.getAnimalName();
        } else {
            writer += " (캐릭터 없음)";
        }

        productReviewDTO.setProductId(productId);
        productReviewDTO.setUserId(userId);
        productReviewDTO.setWriter(writer);

        ProductReviewVO vo = productReviewDTO.toVO();

        int result = productMapper.insertProductReview(vo);
        if (result == 0) {
            throw new NotFoundException("해당 글을 등록할 수 없습니다.");
        }
        return vo.getId();
    }

    @Override
    public Long deleteProductReview(Long id, Long userId) {
        int result = productMapper.deleteProductReview(id, userId);
        if (result == 0) {
            throw new NotFoundException("해당 글을 삭제할 수 없습니다.");
        }
        return id;
    }

    @Override
    public List<ProductDTO<?>> getFilteredProducts(ProductFilterDTO filter) {
        // 입력값 검증
        validateFilter(filter);

        // 상품 조회 (페이징 없이 전체 조회)
        List<org.finmate.product.domain.ProductVO> products = productMapper.getFilteredProductsByType(filter);

        // ProductDTO로 변환하여 반환
        return products.stream()
                .map(ProductDTO::from)
                .collect(Collectors.toList());
    }

    private void validateFilter(ProductFilterDTO filter) {

        // 검색어 전처리
        if (filter.getQuery() != null) {
            filter.setQuery(filter.getQuery().trim());
            if (filter.getQuery().isEmpty()) {
                filter.setQuery(null);
            }
        }
    }

    // 사용자 맞춤 추천 상품
    @Override
    public List<ProductDTO<?>> getCustomizedProducts(Long userId) {

        // 사용자 재무포트폴리오 가져오기
        PortfolioDTO portfolioDTO = PortfolioDTO.from(portfolioMapper.getPortfolio(userId));

        // 사용자 인포
        UserInfoDTO userInfoDTO = UserInfoDTO.from(userInfoMapper.getUserInfoById(userId));

        // 모든 상품 조회
        List<ProductDTO<?>> allProducts = productMapper.getAllProducts()
                .stream()
                .map(ProductDTO::from)
                .collect(Collectors.toList());

        // 거리가 짧은 순으로 정렬
        return allProducts.stream()
                .sorted(Comparator.comparingDouble(product -> getDistance(product, portfolioDTO, userInfoDTO)))
//                .peek(product -> {
//                    double distance = getDistance(product, portfolioDTO, userInfoDTO);
//                    log.info("--------------------------------Product: {}, Distance: {}", product.getName(), distance);
//                })
                .collect(Collectors.toList());
    }

    // 거리 구하는 메소드
    public static double getDistance(ProductDTO<?> productDTO, PortfolioDTO portfolioDTO, UserInfoDTO userInfoDTO){


        /**
         * 상품의 5대 지표 - 사용자의 5대 지표
         */
        Double adventureScore = productDTO.getAdventureScore() - userInfoDTO.getAdventureScore();
        int valueTag = (productDTO.getValueTag().equals(userInfoDTO.getValueTag().name())) ? 1 : 0;
        int speedTage = (productDTO.getSpeedTag().equals(userInfoDTO.getSpeedTag().name())) ? 1 : 0;
        int strategyTag = (productDTO.getStrategyTag().equals(userInfoDTO.getStrategyTag().name())) ? 1 : 0;
        Double minFinanceScore = productDTO.getMinFinanceScore() - userInfoDTO.getFinanceScore();

        /**
         * 회원 가입 설문
         * 상품이 예적금이면 회원가입 필터 적용
         */
        int n = isRequirementViolated(productDTO, userInfoDTO);

        /**
         * 이상적인 재무 포트폴리오 - 사용자의 재무 포트폴리오
         */
        // 사용자의 이상적인 포트폴리오 비율
        int[] standardPortfolio = getRatio(portfolioDTO.getInvestmentProfile());

        // 사용자의 현재 재무 포트폴리오 비율
        int currentCashRatio = (int) ((portfolioDTO.getCash() + portfolioDTO.getDeposit() + portfolioDTO.getSavings()) / portfolioDTO.getTotalAssets() * 100);
        int currentBondRatio = (int) ((portfolioDTO.getBond() / portfolioDTO.getTotalAssets()) * 100);
        int currentFundRatio = (int) ((portfolioDTO.getFund() + portfolioDTO.getStock()) / portfolioDTO.getTotalAssets() * 100);
        int currentEtcRatio = (int) ((portfolioDTO.getOther() / portfolioDTO.getTotalAssets()) * 100);

        // 이상적인 재무 포트폴리오 - 사용자의 재무 포트폴리오
        int CashGap = currentCashRatio - standardPortfolio[0];
        int BondGap = currentBondRatio - standardPortfolio[1];
        int FundGap = currentFundRatio - standardPortfolio[2];
        int EtcGap = currentEtcRatio - standardPortfolio[3];
        int[] diff = new int[]{CashGap, BondGap, FundGap, EtcGap};

        /**
         * 사용자의 투자 성향 요약
         */
        String userProfileSummary = userInfoDTO.getProfileSummary();


        /**
         * 거리 계산 공식 사용
         */

        // 1. (이상 - 현재) 중에서 가장 작은 값 추출
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) if (diff[i] < min) min = diff[i];


        // 2. 모두 양수로 만들기 위해 offset 더함
        int offset = -min + 1; // 최소값이 -30이면 +31
        int[] positiveDiff = new int[4];
        for (int i = 0; i < 4; i++) positiveDiff[i] = diff[i] + offset;


        // 3. 반비례 가중치 = 1 / 값
        double[] inverse = new double[4];
        for (int i = 0; i < 4; i++) inverse[i] = 1.0 / positiveDiff[i];


        // 4. 정규화
        double sum = 0;
        for (double v : inverse) sum += v;

        double[] normalized = new double[4];
        for (int i = 0; i < 4; i++) normalized[i] = inverse[i] / sum;


        /**
         * 상품 유형 별로 가중치 w4 추출
         */
        double w4 = 0.0;
        if(productDTO.getDetail() instanceof SavingsVO) w4 =  normalized[0];
        else if(productDTO.getDetail() instanceof DepositVO) w4 = normalized[0];
        else if(productDTO.getDetail() instanceof FundVO) w4 = normalized[2];
        else w4 = 0;

        /**
         * 거리 계산
         * double
         */
        return (Math.sqrt(Math.pow(adventureScore, 2)
                + Math.pow(valueTag, 2)
                + Math.pow(speedTage, 2)
                + Math.pow(strategyTag, 2)
                + Math.pow(minFinanceScore, 2)
                + 9999 * n) * w4);
    }

    // 회원 가입 설문 일치 여부 확인 메소드
    private static int isRequirementViolated(ProductDTO<?> productDTO, UserInfoDTO userInfoDTO) {
        if(productDTO.getDetail() instanceof SavingsVO userSavingsVO){
            // 조건이 null 이 아닌 경우만 비교
            if (userInfoDTO.getIsMarried() != null && !userSavingsVO.getIsMarried().equals(userInfoDTO.getIsMarried())) return 1;
            if (userInfoDTO.getHasJob() != null && !userSavingsVO.getHasJob().equals(userInfoDTO.getHasJob())) return 1;
            if (userInfoDTO.getUsesPublicTransport() != null && !userSavingsVO.getUsesPublicTransport().equals(userInfoDTO.getUsesPublicTransport())) return 1;
            if (userInfoDTO.getDoesExercise() != null && !userSavingsVO.getDoesExercise().equals(userInfoDTO.getDoesExercise())) return 1;
            if (userInfoDTO.getTravelsFrequently() != null && !userSavingsVO.getTravelsFrequently().equals(userInfoDTO.getTravelsFrequently())) return 1;
            if (userInfoDTO.getHasChildren() != null && !userSavingsVO.getHasChildren().equals(userInfoDTO.getHasChildren())) return 1;
            if (userInfoDTO.getHasHouse() != null && !userSavingsVO.getHasHouse().equals(userInfoDTO.getHasHouse())) return 1;
            if (userInfoDTO.getEmployedAtSme() != null && !userSavingsVO.getEmployedAtSme().equals(userInfoDTO.getEmployedAtSme())) return 1;
            if (userInfoDTO.getUsesMicroloan() != null && !userSavingsVO.getUsesMicroloan().equals(userInfoDTO.getUsesMicroloan())) return 1;
            // gender는 ENUM이니까 equals로 비교
            //if (userInfoDTO.getGender() != null && !userSavingsVO.getGender().equals(userInfoDTO.getGender())) return 1;

            return 0;

        }else if(productDTO.getDetail() instanceof DepositVO userDepositVO){
            // 조건이 null 이 아닌 경우만 비교
            if (userInfoDTO.getIsMarried() != null && !userDepositVO.equals(userInfoDTO.getIsMarried())) return 1;
            if (userInfoDTO.getHasJob() != null && !userDepositVO.getHasJob().equals(userInfoDTO.getHasJob())) return 1;
            if (userInfoDTO.getUsesPublicTransport() != null && !userDepositVO.getUsesPublicTransport().equals(userInfoDTO.getUsesPublicTransport())) return 1;
            if (userInfoDTO.getDoesExercise() != null && !userDepositVO.getDoesExercise().equals(userInfoDTO.getDoesExercise())) return 1;
            if (userInfoDTO.getTravelsFrequently() != null && !userDepositVO.getTravelsFrequently().equals(userInfoDTO.getTravelsFrequently())) return 1;
            if (userInfoDTO.getHasChildren() != null && !userDepositVO.getHasChildren().equals(userInfoDTO.getHasChildren())) return 1;
            if (userInfoDTO.getHasHouse() != null && !userDepositVO.getHasHouse().equals(userInfoDTO.getHasHouse())) return 1;
            if (userInfoDTO.getEmployedAtSme() != null && !userDepositVO.getEmployedAtSme().equals(userInfoDTO.getEmployedAtSme())) return 1;
            if (userInfoDTO.getUsesMicroloan() != null && !userDepositVO.getUsesMicroloan().equals(userInfoDTO.getUsesMicroloan())) return 1;
            // gender는 ENUM이니까 equals로 비교
            //if (userInfoDTO.getGender() != null && !userDepositVO.getGender().equals(userInfoDTO.getGender())) return 1;

            return 0;
        }else return 0;
    }

    // 이상적인 현금/예적금, 채권, 주식/펀드, 기타 비율 정의
    public static final Map<InvestmentProfile, int[]> standardPortfolio = Map.of(
            InvestmentProfile.CONSERVATIVE, new int[]{80, 20, 0, 0},  // 안전형
            InvestmentProfile.CAUTIOUS,     new int[]{50, 30, 20, 0}, // 안정추구형
            InvestmentProfile.BALANCED,     new int[]{20, 40, 40, 0}, // 위험중립형
            InvestmentProfile.DYNAMIC,      new int[]{10, 20, 70, 0}, // 적극투자형
            InvestmentProfile.AGGRESSIVE,   new int[]{0, 10, 80, 10}  // 공격투자형
    );

    // 성향별 이상적인 포트폴리오 비율 가져오는 메소드
    public static int[] getRatio(InvestmentProfile profile) {
        return standardPortfolio.get(profile);
    }
}
