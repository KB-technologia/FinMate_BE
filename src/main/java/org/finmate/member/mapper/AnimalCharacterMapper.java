package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finmate.member.domain.AnimalCharacterVO;

@Mapper
public interface AnimalCharacterMapper {

    // 사용자 캐릭터 조회
    AnimalCharacterVO getCharacterById(Long userId);

    // 캐릭터 랜덤 배정
    Long randomCharacter();
}
