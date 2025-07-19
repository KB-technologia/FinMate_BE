package org.finmate.member.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.CharacterDTO;
import org.finmate.member.mapper.CharacterMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService{

    private final CharacterMapper characterMapper;

    // 트랜잭션 사용 필

    @Override
    public Optional<CharacterDTO> getCharacterById(Long id) {
        return Optional.ofNullable(CharacterDTO.toDTO(characterMapper.getCharacterById(id)));
    }
}
