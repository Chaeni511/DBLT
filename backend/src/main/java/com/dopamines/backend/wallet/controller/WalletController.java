package com.dopamines.backend.wallet.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "wallet", description = "지갑 정보를 관리하는 컨트롤러입니다.")
public class WalletController {
    @GetMapping("/")
    public
}
