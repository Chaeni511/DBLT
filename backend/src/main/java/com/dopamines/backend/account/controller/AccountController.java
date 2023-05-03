package com.dopamines.backend.account.controller;

import com.dopamines.backend.account.dto.AccountRequestDto;
import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.service.AccountService;
import com.dopamines.backend.account.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@RequestMapping("/account")
@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;
    @PutMapping("/nickname")
    public ResponseEntity<Account> editNickname(HttpServletRequest request, @RequestParam String nickname) {
        String user = request.getRemoteUser();
        log.info("nickname에서 찍는 user: " + user);
        //        Long userId = (Long)request.getSession().getAttribute(WebKeys.USER_ID);
        return ResponseEntity.ok(accountService.editNickname(user, nickname));
    }

    @PutMapping("/profileMessage")
    public ResponseEntity<Account> editprofileMessage(HttpServletRequest request, @RequestParam String profileMessage) {
        String user = request.getRemoteUser();
        log.info("profileMessage 찍는 user: " + user);
        return ResponseEntity.ok(accountService.editProfileMessage(user, profileMessage));
    }
}
