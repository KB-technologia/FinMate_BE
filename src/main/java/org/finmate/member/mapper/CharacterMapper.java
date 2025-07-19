package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finmate.member.domain.CharacterVO;

@Mapper
public interface CharacterMapper {

    CharacterVO getCharacterById(Long id);
}
