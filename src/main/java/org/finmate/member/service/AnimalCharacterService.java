package org.finmate.member.service;


import org.finmate.member.dto.AnimalCharacterDTO;

import java.util.Optional;

public interface AnimalCharacterService {

    // 캐릭터 조회
    Optional<AnimalCharacterDTO> getCharacterById(Long userId);

    // 캐릭터 변경
    Optional<AnimalCharacterDTO> changeCharacterById(Long userId, Long characterId);

}
