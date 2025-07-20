package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.AnimalCharacterDTO;
import org.finmate.member.mapper.AnimalCharacterMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalCharacterServiceImpl implements AnimalCharacterService {

    private final AnimalCharacterMapper animalCharacterMapper;

    // 트랜잭션 사용 필

    @Override
    public Optional<AnimalCharacterDTO> getCharacterById(Long id) {
        return Optional.ofNullable(AnimalCharacterDTO.toDTO(animalCharacterMapper.getCharacterById(id)));
    }
}
