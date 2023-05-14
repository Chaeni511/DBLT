package com.dopamines.backend.game.controller;

import com.dopamines.backend.game.dto.MyCharacterDto;
import com.dopamines.backend.game.service.MyCharacterService;
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
@RequestMapping("/item")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MyCharacterController {
    private final MyCharacterService myCharacterService;
    @GetMapping("/myCharacter")
    public ResponseEntity<MyCharacterDto> getMyCharacter(HttpServletRequest request){
        String email = request.getRemoteUser();
        log.info("CharacterController의 getMyCharacter에서 찍는 email: " + email);
        return ResponseEntity.ok(myCharacterService.getMyCharacter(email));

    }
}
