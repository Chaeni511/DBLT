package com.dopamines.backend.fcm.controller;

import com.dopamines.backend.fcm.dto.RequestDto;
import com.dopamines.backend.fcm.service.FCMService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "fcm", description = "firebaseCloudMessage를 관리하는 컨트롤러입니다.")
public class FCMController {

    private final FCMService firebaseCloudMessageService;


//    @PostMapping("/register")
//    @Operation(summary = "fcm deviceToken을 저장하는 api 입니다.", description = "로그인한 기기의 Token을 저장합니다.")
//    public ResponseEntity registerToken(@RequestBody )

    @PostMapping("/push")
    @Operation(summary = "fcm push를 테스트하는 api 입니다.", description = "targetToken, title, body를 작성하여 보내면 firebase 서버에 메세지 푸시를 요청하여 클라이언트에게 메세지를 푸시합니다")
    public ResponseEntity pushMessage(@RequestBody RequestDto requestDto) throws IOException {
        // 1. targetToken, title, body를 작성하여 서버에 보낸다.
        // 2. 서버는 firebase 서버에 메세지 푸시를 요청한다.
        // 3. 토큰이 확인되면 firebase서버는 클라이언트에게 메세지를 푸시한다.

        log.info("Token: " + requestDto.getTargetToken() + " Title: "
                +requestDto.getTitle() + " Body: " + requestDto.getBody());

        firebaseCloudMessageService.sendMessageTo(
                requestDto.getTargetToken(),
                requestDto.getTitle(),
                requestDto.getBody());
        return ResponseEntity.ok().build();
    }

}
