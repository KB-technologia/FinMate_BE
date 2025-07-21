package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.UserInfoVO;
import org.finmate.member.dto.AnimalCharacterDTO;
import org.finmate.member.dto.UserInfoDTO;
import org.finmate.member.mapper.AnimalCharacterMapper;
import org.finmate.member.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AnimalCharacterServiceImpl implements AnimalCharacterService {

    private final AnimalCharacterMapper animalCharacterMapper;
    private final UserInfoMapper userInfoMapper;

    // 트랜잭션 사용 필

    @Override
    public Optional<AnimalCharacterDTO> getCharacterById(Long userId) {
        return Optional.ofNullable(AnimalCharacterDTO.toDTO(animalCharacterMapper.getCharacterById(userId)));
    }

    @Override
    public Optional<AnimalCharacterDTO> changeCharacterById(Long userId, Long characterId) {

        // 유저 정보 불러와서 DTO 변환
        UserInfoDTO userDTO = UserInfoDTO.toDTO(userInfoMapper.getUserInfoById(userId));
        // 사용자가 선택한 캐릭터 ID 넣기
        userDTO.setAnimalId(characterId);
        // 갱신된 user_info VO로 변환 후 저장
        userInfoMapper.insertUserInfo(userDTO.toVO());

        // 캐릭터 DTO 반환
        return Optional.ofNullable(AnimalCharacterDTO.toDTO(animalCharacterMapper.getCharacterById(userId)));
    }
}
