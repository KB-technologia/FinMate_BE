package org.finmate.character.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finmate.character.domain.CharacterVO;

@Mapper
public interface CharacterMapper {

    // 사용자 캐릭터 조회
    CharacterVO getCharacterById(Long userId);

    // 캐릭터 랜덤 배정
    Long randomCharacter();
}
