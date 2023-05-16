package com.dopamines.backend.game.controller;

import com.dopamines.backend.game.dto.MyCharacterDto;
import com.dopamines.backend.game.service.MyCharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/wear")
//    public ResponseEntity wearItem(HttpServletRequest request, @RequestParam int bodies) {
    public ResponseEntity wearItem(HttpServletRequest request,
                                   @RequestParam int bodies,
                                   @RequestParam int bodyParts,
                                   @RequestParam int eyes,
                                   @RequestParam int gloves,
                                   @RequestParam int mouthAndNoses,
                                   @RequestParam int tails
   ) {
//    public ResponseEntity wearItem(HttpServletRequest request, @RequestBody MyCharacterDto myCharacterDto) {

        String email = request.getRemoteUser();

        MyCharacterDto myCharacterDto = new MyCharacterDto(bodies, bodyParts, eyes, gloves, mouthAndNoses, tails);
        try {
            myCharacterService.wearItem(email, myCharacterDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
