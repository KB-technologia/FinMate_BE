package org.finmate.member.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finmate.member.dto.CharacterDTO;
import org.finmate.member.service.CharacterService;
import org.finmate.member.service.CharacterServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/character")
@Api(tags = "캐릭터 API")
public class CharacterController {

    private final CharacterServiceImpl characterService;

    /**
     * 캐릭터 조회
     */
//    @GetMapping("")
//    public CharacterDTO getCharacter(Long userId){
//
//    }
}
