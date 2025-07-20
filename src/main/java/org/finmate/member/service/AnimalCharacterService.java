package org.finmate.member.service;


import org.finmate.member.dto.AnimalCharacterDTO;

import java.util.Optional;

public interface AnimalCharacterService {

    Optional<AnimalCharacterDTO> getCharacterById(Long id);

}
