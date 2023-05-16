package com.dopamines.backend.fcm.controller;

import com.dopamines.backend.fcm.dto.GroupTokenListDto;
import com.dopamines.backend.fcm.dto.RequestDto;
import com.dopamines.backend.fcm.dto.TokenDto;
import com.dopamines.backend.fcm.service.FCMService;
import com.dopamines.backend.fcm.service.FCMServiceImpl;
import com.dopamines.backend.wallet.dto.SettlementResultDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "fcm", description = "firebaseCloudMessage를 관리하는 컨트롤러입니다.")
public class FCMController {

    private final FCMService fcmService;


    @PostMapping("/register")
    @Operation(summary = "fcm deviceToken을 저장하는 api 입니다.", description = "로그인한 기기의 Token을 저장합니다.")
    public ResponseEntity<Void> registerToken(HttpServletRequest request, @RequestBody TokenDto tokenDto) {

        try {
            // 헤더에서 유저 이메일 가져옴
            String userEmail = request.getRemoteUser();
            fcmService.registerToken(userEmail, tokenDto.getDeviceToken());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error("API 호출 중 예외 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("API 호출 중 예외 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    @Operation(summary = "fcm deviceToken을 갱신하는 api 입니다.", description = "로그인한 기기의 Token을 갱신합니다.")
    public ResponseEntity<Void> updateToken(HttpServletRequest request, @RequestBody TokenDto tokenDto) {

        try {
            // 헤더에서 유저 이메일 가져옴
            String userEmail = request.getRemoteUser();
            fcmService.updateToken(userEmail, tokenDto.getDeviceToken());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error("API 호출 중 예외 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("API 호출 중 예외 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/delete")
    @Operation(summary = "fcm deviceToken을 삭제하는 api 입니다.", description = "로그아웃시 기기의 Token을 삭제제합니다")
    public ResponseEntity<Void> deleteToken(HttpServletRequest request) {

        try {
            // 헤더에서 유저 이메일 가져옴
            String userEmail = request.getRemoteUser();
            fcmService.deleteToken(userEmail);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error("API 호출 중 예외 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("API 호출 중 예외 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/groupToken")
    @Operation(summary = "같은 그룹에 있는 deviceToken의 리스트를 불러오는 api 입니다.", description = "로그아웃시 기기의 Token을 삭제합니다")
    public ResponseEntity<GroupTokenListDto> getGroupToken(@RequestParam Long planId) {

        try {
            GroupTokenListDto groupTokenListDto = fcmService.getGroupToken(planId);
            return ResponseEntity.ok(groupTokenListDto);
        } catch (IllegalArgumentException e) {
            // 약속 정보가 없는 경우에 대한 예외 처리
            log.error("API 호출 중 예외 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("API 호출 중 예외 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/push")
    @Operation(summary = "fcm push를 테스트하는 api 입니다.", description = "targetToken, title, body를 작성하여 보내면 firebase 서버에 메세지 푸시를 요청하여 클라이언트에게 메세지를 푸시합니다")
    public ResponseEntity<Void> pushMessage(@RequestBody RequestDto requestDto) throws IOException {
        // 1. targetToken, title, body를 작성하여 서버에 보낸다.
        // 2. 서버는 firebase 서버에 메세지 푸시를 요청한다.
        // 3. 토큰이 확인되면 firebase서버는 클라이언트에게 메세지를 푸시한다.

        log.info("Token: " + requestDto.getTargetToken() + " Title: "
                +requestDto.getTitle() + " Body: " + requestDto.getBody());

        fcmService.sendMessageTo(
                requestDto.getTargetToken(),
                requestDto.getTitle(),
                requestDto.getBody());
        return ResponseEntity.ok().build();
    }

}
