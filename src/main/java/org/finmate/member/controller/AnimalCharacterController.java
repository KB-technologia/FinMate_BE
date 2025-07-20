package org.finmate.member.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public ResponseEntity<AnimalCharacterDTO> getCharacter(@PathVariable Long userId){
        return ResponseEntity.of(characterService.getCharacterById(userId));
    }

    @PostMapping("")
    public ResponseEntity<AnimalCharacterDTO> changeCharacter(@RequestBody AnimalChangeDTO animalChangeDTO){
        Long userId = animalChangeDTO.getUserId();
        Long animalId = animalChangeDTO.getAnimalId();

        return ResponseEntity.of(characterService.changeCharacterById(userId, animalId));
    }

}
