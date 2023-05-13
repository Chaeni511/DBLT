package com.dopamines.backend.wallet.service;

import com.dopamines.backend.wallet.dto.SettlementResultDto;

public interface WalletService {

    public SettlementResultDto settleMoney(String userEmail, Long planId);

}
