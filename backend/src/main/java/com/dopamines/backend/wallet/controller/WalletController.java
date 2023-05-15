package com.dopamines.backend.wallet.controller;

import com.dopamines.backend.wallet.dto.WalletDto;
import com.dopamines.backend.wallet.service.WalletService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "wallet", description = "지갑 정보를 관리하는 컨트롤러입니다.")
public class WalletController {
    private final WalletService walletService;
    @GetMapping("/details")
    public ResponseEntity<WalletDto> getWalletDetails(HttpServletRequest request){
        String email = request.getRemoteUser();
        return ResponseEntity.ok(walletService.getWalletDetails(email));
    }

    @PostMapping("/charge")
    public ResponseEntity<WalletDto> chargeWallet(
            HttpServletRequest request,
            int money,
            String method,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate transactionDate,
            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") LocalTime transactionTime,
            String receipt
    ) {
        String email = request.getRemoteUser();
        walletService.chargeWallet(email, money, method, transactionDate, transactionTime, receipt);
        return ResponseEntity.ok(walletService.getWalletDetails(email));
    }


    @PostMapping("/transfer")
    public void transferWallet(HttpServletRequest request, int money, Long planId) {

    }
}
