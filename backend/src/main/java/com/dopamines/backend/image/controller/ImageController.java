package com.dopamines.backend.image.controller;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.account.service.AccountService;
import com.dopamines.backend.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequestMapping("/image")
@RequiredArgsConstructor
@RestController
public class ImageController {
    private final ImageService imageService;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @PutMapping("/profile")
    public ResponseEntity<Account> editProfile(HttpServletRequest request, @RequestParam MultipartFile file) throws IOException {
        String email = request.getRemoteUser();
        log.info("image/profile에서 찍는 user: " + email);

        String profile = imageService.saveImage(file, "profile");
        Optional<Account> optional = accountRepository.findByEmail(email);
        Account account = null;
        if(optional.isEmpty()) {
            account = new Account();
            log.info("AccountServiceImpl의 editNickname에서");
        }else {
            account = optional.get();
            account.setProfile(profile);
        }
        return ResponseEntity.ok(account);
    }
}
