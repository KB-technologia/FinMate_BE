package org.finmate.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.assessment.dto.AssessmentDTO;
import org.finmate.member.dto.AnimalChangeDTO;
import org.finmate.member.dto.AnimalCharacterDTO;
import org.finmate.member.service.AnimalCharacterServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/character")
@Api(tags = "캐릭터 API")
public class AnimalCharacterController {

    private final AnimalCharacterServiceImpl characterService;

    /**
     * 캐릭터 조회
     * userId는 추후 토큰 형식으로 리팩토링 예정
     */
    @GetMapping("{userId}")
    @ApiOperation(
            value = "캐릭터 조회",
            notes = "사용자 ID를 기반으로 현재 사용자의 캐릭터 조회"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 캐릭터를 조회했습니다.",
                    response = AnimalCharacterDTO.class, responseContainer = "AnimalCharacterDTO"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<AnimalCharacterDTO> getCharacter(@PathVariable Long userId){
        return ResponseEntity.of(characterService.getCharacterById(userId));
    }

    /**
     * 캐릭터 변경
     */
    @PostMapping("")
    @ApiOperation(
            value = "캐릭터 변경",
            notes = "사용자 ID와 캐릭터 ID을 통해 사용자의 인포 테이블 갱신"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "정상적으로 캐릭터를 변경했습니다.",
                    response = AnimalCharacterDTO.class, responseContainer = "AnimalCharacterDTO"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<AnimalCharacterDTO> changeCharacter(@RequestBody AnimalChangeDTO animalChangeDTO){
        Long userId = animalChangeDTO.getUserId();
        Long animalId = animalChangeDTO.getAnimalId();

        return ResponseEntity.of(characterService.changeCharacterById(userId, animalId));
    }

}
