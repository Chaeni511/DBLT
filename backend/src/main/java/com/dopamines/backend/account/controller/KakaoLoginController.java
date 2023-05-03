package com.dopamines.backend.account.controller;

import com.dopamines.backend.account.config.KakaoLoginConfig;
import com.dopamines.backend.account.config.KakaoUserInfo;
import com.dopamines.backend.account.dto.AccountRequestDto;
import com.dopamines.backend.account.dto.KakaoUserInfoResponseDto;
import com.dopamines.backend.account.dto.RoleToUserRequestDto;
import com.dopamines.backend.account.dto.TokenResponseDto;
import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.dopamines.backend.account.security.JwtConstants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@RequestMapping("/account")
@RequiredArgsConstructor
@RestController
public class KakaoLoginController {

    private final AccountService accountService;
    @Autowired
    private final KakaoUserInfo kakaoUserInfo;
    @Autowired
    private final KakaoLoginConfig kakaoLoginConfig;
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/oauth")
    @ResponseBody
    public ResponseEntity<AccountRequestDto> kakaoOauth(@RequestParam("code") String code) throws IOException {

        System.out.println("code: " + code);

        KakaoUserInfoResponseDto userInfo = kakaoUserInfo.getUserInfo(code);
        System.out.println("회원 정보 입니다.{}" + userInfo);

        String email = userInfo.getKakao_account().getEmail();
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getKakao_account().getProfile().getNickname();

        Optional<Account> optional = accountRepository.findByEmail(email);
        System.out.println("KakaoLoginController에서 찍는 optional: " + optional);

        Boolean signup;
        // 회원이 아니면 회원가입
        if (optional.isEmpty()) {
           signup = false;

            // default 프사 이미지 url 생기면 판별 후 profile에 url 추가 필수!!
//            signup(accountRequestDto);
        } else {
            signup = true;
        }

        AccountRequestDto  accountRequestDto = new AccountRequestDto(signup, email, kakaoId.toString(), nickname);

        return ResponseEntity.ok(accountRequestDto);

    }


    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody AccountRequestDto dto) {
        return ResponseEntity.ok(accountService.saveAccount(dto));
    }

    @PostMapping("/role")
    public ResponseEntity<Long> saveRole(@RequestBody String roleName) {
        return ResponseEntity.ok(accountService.saveRole(roleName));
    }

    @PostMapping("/userrole")
    public ResponseEntity<Long> addRoleToUser(@RequestBody RoleToUserRequestDto dto) {
        return ResponseEntity.ok(accountService.addRoleToUser(dto));
    }

    @GetMapping("/my")
    public ResponseEntity<String> my() {
//        System.out.println();
        return ResponseEntity.ok("My");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Admin");
    }

    @GetMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            throw new RuntimeException("AccountApiController에서 JWT Token이 존재하지 않습니다.");
        }
        String refreshToken = authorizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Map<String, String> tokens = accountService.refresh(refreshToken);
        response.setHeader(AT_HEADER, tokens.get(AT_HEADER));
        if (tokens.get(RT_HEADER) != null) {
            response.setHeader(RT_HEADER, tokens.get(RT_HEADER));
        }
        return ResponseEntity.ok(tokens);
    }
}
