package org.finmate.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.adapter.mydata.MyDataApi;
import org.finmate.adapter.mydata.dto.MyDataResponseDTO;
import org.finmate.exception.NotFoundException;
import org.finmate.portfolio.domain.PortfolioVO;
import org.finmate.portfolio.dto.PortfolioDTO;
import org.finmate.portfolio.dto.PortfolioRequestDTO;
import org.finmate.portfolio.mapper.PortfolioMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * 포트폴리오 서비스 구현 클래스, 사용자의 포트폴리오 생성, 조회, 수정, 삭제, 이력 조회를 처리
 * @author 소영재
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioMapper portfolioMapper;
    private final  MyDataApi myDataApi;
    /**
     * 포트폴리오 생성
     * @param userId 유저 아이디
     * @param portfolioRequestDTO 유저에게 입력 받는 값(cash, other(기타자산))
     */
    @Override
    public void createPortfolio(final Long userId, final PortfolioRequestDTO portfolioRequestDTO) {

        MyDataResponseDTO myData = myDataApi.getMyData(userId);
        PortfolioDTO portfolioDTO = myData.toPortfolioDTO();
        portfolioDTO.setUserId(userId);
        portfolioDTO.setCash(portfolioRequestDTO.getCash());
        portfolioDTO.setOther(portfolioRequestDTO.getOther());
        portfolioDTO.update();
        portfolioMapper.insertPortfolio(portfolioDTO.toVO());
    }

    /**
     * userId에 해당하는 포트폴리오 조회
     * @param userId
     */
    @Override
    public PortfolioDTO getPortfolioByUserId(final Long userId) {
        PortfolioVO vo = portfolioMapper.getPortfolio(userId);
        if(vo== null){
            throw new NotFoundException("해당 사용자의 포트폴리오가 존재하지 않습니다.");
        }
        return PortfolioDTO.from(vo);
    }

    /**
     * 포트폴리오 수정 메서드
     * @param userId
     * @param portfolioRequestDTO
     */
    @Override
    public void updatePortfolio(final Long userId, final PortfolioRequestDTO portfolioRequestDTO) {
        PortfolioDTO portfolioDTO = getPortfolioByUserId(userId);
        portfolioDTO.setCash(portfolioRequestDTO.getCash());
        portfolioDTO.setOther(portfolioRequestDTO.getOther());
        portfolioDTO.update();
        PortfolioVO vo = portfolioDTO.toVO();

        portfolioMapper.updatePortfolio(vo);
    }

    /**
     * userId에 해당하는 포트폴리오 삭제
     * @param userId
     */
    @Override
    public void deletePortfolioByUserId(final Long userId) {
        portfolioMapper.deletePortfolio(userId);
    }

    /**
     * 과거의 포트폴리오 이력을 조회하는 메서드
     * @param userId
     * @param date
     */
    @Override
    public PortfolioDTO getHistoryPortfolioByUserId(final Long userId, final LocalDate date) {
        LocalDate startDate = date;
        LocalDate endDate = date.plusDays(1);

        PortfolioVO vo = portfolioMapper.getHistoryPortfolio(userId, startDate, endDate);
        if(vo== null){
            throw new NotFoundException("해당 사용자의 과거 포트폴리오가 존재하지 않습니다.");
        }
        return PortfolioDTO.from(vo);
    }

}
