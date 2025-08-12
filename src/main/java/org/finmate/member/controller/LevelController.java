package org.finmate.member.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.CustomUser;
import org.finmate.member.dto.LevelRequestDTO;
import org.finmate.member.dto.LevelResponseDTO;
import org.finmate.member.mapper.UserInfoMapper;
import org.finmate.member.service.LevelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/level")
@Api(tags = "레벨 API")
public class LevelController {

    private final LevelService levelService;

    /**
     * 사용자 레벨 조회
     */
    @GetMapping
    public ResponseEntity<LevelResponseDTO> getLevel(@ApiIgnore @AuthenticationPrincipal CustomUser customUser){

        Long userId = customUser.getUser().getId();
        return ResponseEntity.ok(levelService.getLevel(userId));
    }

    /**
     * 사용자 경험치에 따른 레벨 증가 및 캐릭터 변경권 반환
     */
    @PostMapping
    public ResponseEntity<LevelResponseDTO> processLevelUp(@ApiIgnore @AuthenticationPrincipal CustomUser customUser, @RequestBody LevelRequestDTO dto){

        Long userId = customUser.getUser().getId();
        return ResponseEntity.ok(levelService.processLevelUp(userId, dto.getExp()));
    }
}
