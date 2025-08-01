package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.AnimalCharacterVO;
import org.finmate.member.dto.AnimalCharacterDTO;
import org.finmate.member.dto.UserInfoDTO;
import org.finmate.member.mapper.AnimalCharacterMapper;
import org.finmate.member.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalCharacterServiceImpl implements AnimalCharacterService {

    private final AnimalCharacterMapper animalCharacterMapper;
    private final UserInfoMapper userInfoMapper;


    // 캐릭터 조회
    @Override
    public Optional<AnimalCharacterDTO> getCharacterById(Long userId) {

        // 사용자 레벨 추출
        int userLevel = userInfoMapper.getUserInfoById(userId).getUserLevel();
        AnimalCharacterVO userCharacter = animalCharacterMapper.getCharacterById(userId);

        // 사용자 레벨에 맞는 이미지 추출
        String userCharacterImage = getUserCharacterImage(userLevel, userCharacter);

        return Optional.ofNullable(AnimalCharacterDTO.from(userCharacter.getAnimalName(), userCharacterImage));
    }


    // 캐릭터 변경
    @Transactional
    @Override
    public Optional<AnimalCharacterDTO> changeCharacterById(Long userId, Long characterId) {

        // 유저 정보 불러와서 DTO 변환(VO 에는 setter 없기 때문에 DTO 로 변환)
        UserInfoDTO userDTO = UserInfoDTO.from(userInfoMapper.getUserInfoById(userId));

        if(userDTO.getCharacterTicket() > 0){
            // 사용자가 선택한 캐릭터 ID 넣기
            userDTO.setAnimalId(characterId);
            // 캐릭터 변경권 -1
            userDTO.setCharacterTicket(userDTO.getCharacterTicket()-1);
            // 갱신된 user_info VO로 변환 후 저장
            userInfoMapper.insertUserInfo(userDTO.toVO());

            // 사용자 캐릭터 추출
            AnimalCharacterVO userCharacter = animalCharacterMapper.getCharacterById(userId);
            // 사용자 레벨에 맞는 이미지 추출
            String userCharacterImage = getUserCharacterImage(userDTO.getUserLevel(), userCharacter);

            // 캐릭터 DTO 반환
            return Optional.ofNullable(AnimalCharacterDTO.from(userCharacter.getAnimalName(), userCharacterImage));
        }else{
            throw new IllegalStateException("캐릭터 변경권이 부족합니다.");
        }
    }

    private static String getUserCharacterImage(int userLevel, AnimalCharacterVO userCharacter) {

        String userCharacterImage;
        if (userLevel >= 30) {
            userCharacterImage =  "/resources/dragon.png"; // 전설의 용
        } else if (userLevel >= 16) {
            userCharacterImage =  userCharacter.getAnimalImage4(); // 동물 모습
        } else if (userLevel >= 10) {
            userCharacterImage =  userCharacter.getAnimalImage3(); // 보따리
        } else if (userLevel >= 4) {
            userCharacterImage =  userCharacter.getAnimalImage2(); // 깨진 알
        } else {
            userCharacterImage =  userCharacter.getAnimalImage1(); // 알
        }
        return userCharacterImage;
    }
}
