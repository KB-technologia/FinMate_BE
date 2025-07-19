package org.finmate.member.service;


import org.finmate.member.dto.CharacterDTO;

import java.util.Optional;

public interface CharacterService {

    Optional<CharacterDTO> getCharacterById(Long id);

}
