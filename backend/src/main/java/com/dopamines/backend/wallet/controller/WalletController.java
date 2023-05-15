package com.dopamines.backend.wallet.controller;

import com.dopamines.backend.wallet.dto.ChargeRequestDto;
import com.dopamines.backend.wallet.dto.WalletDetailDto;
import com.dopamines.backend.wallet.dto.WalletDto;
import com.dopamines.backend.wallet.service.WalletService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity chargeWallet(HttpServletRequest request, ChargeRequestDto chargeRequestDto) {
        String email = request.getRemoteUser();
        walletService.chargeWallet(email, chargeRequestDto);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/transfer")
    public void transferWallet(HttpServletRequest request, int money, Long planId) {

    }
}
