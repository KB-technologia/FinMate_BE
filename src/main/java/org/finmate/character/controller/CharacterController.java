package org.finmate.character.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.member.domain.CustomUser;
import org.finmate.character.dto.CharacterChangeRequestDTO;
import org.finmate.character.dto.CharacterResponseDTO;
import org.finmate.character.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/character")
@Api(tags = "캐릭터 API")
public class CharacterController {

    private final CharacterService characterService;

    /**
     * 캐릭터 조회
     */
    @GetMapping
    @ApiOperation(
            value = "캐릭터 조회",
            notes = "사용자 ID를 기반으로 현재 사용자의 캐릭터 조회"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 캐릭터를 조회했습니다.",
                    response = CharacterResponseDTO.class)
    })
    public ResponseEntity<CharacterResponseDTO> getCharacter(@ApiIgnore @AuthenticationPrincipal final CustomUser customUser){
        return ResponseEntity.ok(characterService.getCharacterById(customUser.getUser().getId()));
    }

    /**
     * 캐릭터 변경
     */
    @PostMapping
    @ApiOperation(
            value = "캐릭터 변경",
            notes = "사용자 ID와 캐릭터 ID을 통해 사용자의 인포 테이블 갱신"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 캐릭터를 변경했습니다.",
                    response = CharacterResponseDTO.class)
    })
    public ResponseEntity<CharacterResponseDTO> changeCharacter(
            @ApiIgnore @AuthenticationPrincipal final CustomUser customUser,
            @RequestBody final CharacterChangeRequestDTO characterChangeRequestDTO)
    {
        Long userId = customUser.getUser().getId();
        Long animalId = characterChangeRequestDTO.getAnimalId();

        return ResponseEntity.ok(characterService.changeCharacterById(userId, animalId));
    }

}
