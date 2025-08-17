package org.finmate.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.CustomUser;
import org.finmate.member.dto.LevelRequestDTO;
import org.finmate.member.dto.LevelResponseDTO;
import org.finmate.member.mapper.UserInfoMapper;
import org.finmate.member.service.LevelService;
import org.finmate.security.dto.AuthResultDTO;
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
    @ApiOperation(value = "사용자 레벨 조회", notes = "사용자 주입을 통해 현재 사용자 레벨 반환")
    @ApiResponse(code = 200, message = "정상적으로 요청이 처리되었습니다.", response = LevelResponseDTO.class)
    public ResponseEntity<LevelResponseDTO> getLevel(@ApiIgnore @AuthenticationPrincipal CustomUser customUser){

        Long userId = customUser.getUser().getId();
        return ResponseEntity.ok(levelService.getLevel(userId));
    }

    /**
     * 사용자 경험치에 따른 레벨 증가 및 캐릭터 변경권 반환
     */
    @PostMapping
    @ApiOperation(value = "사용자 레벨 증가 요청", notes = "사용자 경험치에 따른 레벨 증가 및 캐릭터 변경권 반환")
    @ApiResponse(code = 200, message = "정상적으로 요청이 처리되었습니다.", response = LevelResponseDTO.class)
    public ResponseEntity<LevelResponseDTO> processLevelUp(@ApiIgnore @AuthenticationPrincipal CustomUser customUser, @RequestBody LevelRequestDTO dto){

        Long userId = customUser.getUser().getId();
        return ResponseEntity.ok(levelService.processLevelUp(userId, dto.getExp()));
    }
}
