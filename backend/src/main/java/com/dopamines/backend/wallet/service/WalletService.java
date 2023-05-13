package com.dopamines.backend.wallet.service;

import com.dopamines.backend.wallet.dto.SettlementResultDto;

public interface WalletService {

    // 정산하기
    public SettlementResultDto settleMoney(String userEmail, Long planId);

}
