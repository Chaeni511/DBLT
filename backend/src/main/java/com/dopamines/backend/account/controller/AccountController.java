package com.dopamines.backend.account.controller;

import com.dopamines.backend.account.dto.AccountRequestDto;
import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.service.AccountService;
import com.dopamines.backend.account.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequestMapping("/account")
@RequiredArgsConstructor
@RestController
public class AccountController {
    AccountService accountService;
    @PostMapping("/nickname")
    public ResponseEntity<Account> editNickname(HttpServletRequest request, @RequestParam String nickname) {
        String user = request.getRemoteUser();
//        Long userId = (Long)request.getSession().getAttribute(WebKeys.USER_ID);
        return ResponseEntity.ok(accountService.editNickname(nickname));
    }
}
