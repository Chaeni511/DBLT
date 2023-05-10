package com.dopamines.backend.game.controller;

import com.dopamines.backend.game.entity.Character;
import com.dopamines.backend.game.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CharacterController {
    CharacterService characterService;
    @GetMapping("/myCharcter")
    public ResponseEntity<Character> getMyCharacter(HttpServletRequest request){
        String email = request.getRemoteUser();
        return ResponseEntity.ok(characterService.getMyCharacter(email));

    }
}
