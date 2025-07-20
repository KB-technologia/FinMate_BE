package org.finmate.member.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.member.dto.AnimalCharacterDTO;
import org.finmate.member.service.AnimalCharacterServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/character")
@Api(tags = "캐릭터 API")
public class CharacterController {

    private final AnimalCharacterServiceImpl characterService;

    /**
     * 캐릭터 조회
     */
    @GetMapping("{userId}")
    public ResponseEntity<AnimalCharacterDTO> getCharacter(@PathVariable Long userId){
        return ResponseEntity.of(characterService.getCharacterById(userId));
    }
}
