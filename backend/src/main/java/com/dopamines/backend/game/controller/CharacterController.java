package com.dopamines.backend.game.controller;

import com.dopamines.backend.game.service.MyCharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CharacterController {
    private final MyCharacterService characterService;
//    @GetMapping("/myCharcter")
//    public ResponseEntity<Character> getMyCharacter(HttpServletRequest request){
//        String email = request.getRemoteUser();
//        log.info("CharacterController의 getMyCharacter에서 찍는 email: " + email);
//        return ResponseEntity.ok(characterService.getMyCharacter(email));
//
//    }
}
