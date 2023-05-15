package com.dopamines.backend.fcm.controller;

import com.dopamines.backend.fcm.dto.RequestDto;
import com.dopamines.backend.fcm.service.FcmService;
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
@Api(value = "fcm", description = "firebaseCloudMessageService를 관리하는 컨트롤러입니다.")
public class FcmController {
    private final FcmService firebaseCloudMessageService;

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
