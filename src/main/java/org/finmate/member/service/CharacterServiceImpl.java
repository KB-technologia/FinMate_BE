package org.finmate.member.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.finmate.member.mapper.CharacterMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class CharacterServiceImpl implements CharacterService{

    private final CharacterMapper characterMapper;

    // 트랜잭션 사용 필
}
