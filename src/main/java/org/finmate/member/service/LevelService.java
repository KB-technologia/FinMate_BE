package org.finmate.member.service;

import org.finmate.member.dto.LevelResponseDTO;

public interface LevelService {

    public LevelResponseDTO getLevel(Long userId);

    // 캐릭터 경험치 증가 POST
    public LevelResponseDTO processLevelUp(Long userId, int gainedExp);
}
