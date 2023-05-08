package com.dopamines.backend.account.controller;

import com.dopamines.backend.account.dto.SearchResponseDto;
import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        String email = request.getRemoteUser();
        log.info("profileMessage 찍는 user: " + email);
        return ResponseEntity.ok(accountService.editProfileMessage(email, profileMessage));
    }

    @PutMapping("/delete")
    public ResponseEntity deleteAccount(HttpServletRequest request){
        String email = request.getRemoteUser();
        accountService.deleteAccount(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchResponseDto>> searchNickname(@RequestParam String keyword){
        return ResponseEntity.ok(accountService.searchNickname(keyword));
    }

}
