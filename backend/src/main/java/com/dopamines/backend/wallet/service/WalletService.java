package com.dopamines.backend.wallet.service;

import com.dopamines.backend.wallet.dto.ChargeRequestDto;
import com.dopamines.backend.wallet.dto.SettlementResultDto;
import com.dopamines.backend.wallet.dto.WalletDetailDto;
import com.dopamines.backend.wallet.dto.WalletDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WalletService {

    // 정산하기
    public SettlementResultDto settleMoney(String userEmail, Long planId);

    // 내역 가져오기
    public WalletDto getWalletDetails(String email);
    public void chargeWallet(String eamil, ChargeRequestDto chargeRequestDto)
}
