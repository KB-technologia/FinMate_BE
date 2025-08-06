package org.finmate.character.service;


import org.finmate.character.dto.CharacterResponseDTO;

import java.util.Optional;

public interface CharacterService {

    // 캐릭터 조회
    Optional<CharacterResponseDTO> getCharacterById(Long userId);

    // 캐릭터 변경
    Optional<CharacterResponseDTO> changeCharacterById(Long userId, Long characterId);

}
