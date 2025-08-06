package org.finmate.assessment.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.UserInfoVO;
import org.finmate.member.domain.enums.SpeedTag;
import org.finmate.member.domain.enums.StrategyTag;
import org.finmate.member.domain.enums.ValueTag;
import org.finmate.member.dto.UserInfoDTO;
import org.finmate.character.mapper.CharacterMapper;
import org.finmate.member.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService{

    private final UserInfoMapper userInfoMapper;
    private final CharacterMapper characterMapper;


    @Override
    @Transactional
    public UserInfoDTO resultAssessment(final Long userId, final List<Integer> choice) {

        // 5대 지표 계산

        Double adventureScore = (double) ((choice.get(0) + choice.get(1)) / 2.0); // 모험성향
        ValueTag valueTag = ValueTag.fromCode(choice.get(2)); // 가치관 = 투자 목적
        SpeedTag speedTag = SpeedTag.fromCode(choice.get(3)); // 속도 = 투자기간
        StrategyTag strategyTag = StrategyTag.fromCode(choice.get(4)); // 운/전략 = 투자 전략
        Double financeScore = (double) ((choice.get(5))); // 재정체력


        // 사용자 성향 요약
        // 모든 문항의 합 sum
        double sum = 0;
        for (Integer i : choice) sum += i;
        sum /= choice.size();

        String profileSummary = getSummary(sum);

        // user_info 에 저장 및 캐릭터 생성
        Long randomAnimal = characterMapper.randomCharacter();

        UserInfoVO userInfoVO = UserInfoVO
                .builder()
                .userId(userId)
                .animalId(randomAnimal)
                .exp(0)
                .profileSummary(profileSummary)
                .adventureScore(adventureScore)
                .valueTag(valueTag)
                .speedTag(speedTag)
                .strategyTag(strategyTag)
                .financeScore(financeScore)
                .updatedAt(LocalDateTime.now())
                .build();

        //TODO: 예외처리 추가해야됨
        userInfoMapper.insertUserInfo(userInfoVO);
        return UserInfoDTO.from(userInfoVO);
    }

    private static String getSummary(double sum) {

        String profileSummary = "";

        String[] stable = {"소심한", "느긋한"};
        String[] delicate  = {"신중한", "섬세한"};
        String[] neutrality = {"현실적인", "전략적인"};
        String[] active = {"도전적인", "과감한"};
        String[] offensive = {"용맹한", "에너지 넘치는"};


        Random rnd = new Random();
        String pick1 = stable[rnd.nextInt(stable.length)];
        String pick2 = delicate[rnd.nextInt(delicate.length)];
        String pick3 = neutrality[rnd.nextInt(neutrality.length)];
        String pick4 = active[rnd.nextInt(active.length)];
        String pick5 = offensive[rnd.nextInt(offensive.length)];

        if(sum < 0.6) profileSummary = pick1;
        else if(sum >= 0.6 && sum < 1.2) profileSummary = pick2;
        else if(sum >= 1.2 && sum < 1.8) profileSummary = pick3;
        else if(sum >= 1.8 && sum < 2.4) profileSummary = pick4;
        else if(sum >= 2.4 && sum <= 3.0) profileSummary = pick5;

        return profileSummary;
    }
}
