package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finmate.member.domain.AnimalCharacterVO;

@Mapper
public interface AnimalCharacterMapper {

    AnimalCharacterVO getCharacterById(Long userId);
}
