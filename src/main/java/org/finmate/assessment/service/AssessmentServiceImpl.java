package org.finmate.assessment.service;

import lombok.RequiredArgsConstructor;
import org.finmate.assessment.dto.AssessmentDTO;
import org.finmate.assessment.mapper.AssessmentMapper;
import org.finmate.member.domain.UserInfoVO;
import org.finmate.member.domain.enums.SpeedTag;
import org.finmate.member.domain.enums.StrategyTag;
import org.finmate.member.domain.enums.ValueTag;
import org.finmate.member.dto.UserInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService{

    private final AssessmentMapper assessmentMapper;

    @Override
    public List<AssessmentDTO> loadAssessment() {

        return assessmentMapper.getList()
                .stream()
                .map(AssessmentDTO::toDTO)
                .toList();

    }

    @Override
    @Transactional
    public Optional<UserInfoDTO> resultAssessment(Long userId, List<Integer> choice) {

        /**
         * 5대 지표 계산
         */
        Double adventure_score = (double) ((choice.get(0) + choice.get(1)) / 2.0); // 모험성향
        ValueTag valueTag = ValueTag.fromCode(choice.get(2)); // 가치관 = 투자 목적
        SpeedTag speedTag = SpeedTag.fromCode(choice.get(3)); // 속도 = 투자기간
        StrategyTag strategyTag = StrategyTag.fromCode(choice.get(4)); // 운/전략 = 투자 전략
        Double financeScore = (double) ((choice.get(5) + choice.get(6)) / 2.0); // 재정체력


        /**
         * 사용자 성향 요약
         */
        String[] stable = {"소심한", "느긋한"};
        String[] delicate  = {"신중한", "섬세한"};
        String[] neutrality = {"현실적인", "전략적인"};
        String[] active = {"도전적인", "과감한"};
        String[] offensive = {"용맹한", "에너지 넘치는"};

        // 1번 문항 + 2번 문항 + 6번 문항 + 7번 문항 만 계산.
        double sum = (double) (choice.get(0) + choice.get(1) + choice.get(5) + choice.get(6)) / 4;
        String profile_summary = "";

        Random rnd = new Random();
        String pick1 = stable[rnd.nextInt(stable.length)];
        String pick2 = delicate[rnd.nextInt(delicate.length)];
        String pick3 = neutrality[rnd.nextInt(neutrality.length)];
        String pick4 = active[rnd.nextInt(active.length)];
        String pick5 = offensive[rnd.nextInt(offensive.length)];

        if(sum < 0.6) profile_summary = pick1;
        else if(sum >= 0.6 && sum < 1.2) profile_summary = pick2;
        else if(sum >= 1.2 && sum < 1.8) profile_summary = pick3;
        else if(sum >= 1.8 && sum < 2.4) profile_summary = pick4;
        else if(sum >= 2.4 && sum <= 3.0) profile_summary = pick5;


        /**
         * user_info 에 저장 및 캐릭터 생성
         */
        Long[] animalIdList = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L};
        Long randomAnimal = animalIdList[rnd.nextInt(animalIdList.length)];

        UserInfoVO userInfoVO = UserInfoVO
                .builder()
                .userId(userId)
                .animalId(randomAnimal)
                .exp(0)
                .profileSummary(profile_summary)
                .adventureScore(adventure_score)
                .valueTag(valueTag)
                .speedTag(speedTag)
                .strategyTag(strategyTag)
                .financeScore(financeScore)
                .updatedAt(LocalDateTime.now())
                .build();

        assessmentMapper.insertUserInfo(userInfoVO);
        return Optional.ofNullable(UserInfoDTO.toDTO(userInfoVO));
    }
}
