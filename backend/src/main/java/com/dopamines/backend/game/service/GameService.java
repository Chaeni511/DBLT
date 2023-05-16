package com.dopamines.backend.game.service;

import com.dopamines.backend.game.dto.GameResponseDto;

public interface GameService {

    GameResponseDto enterGame(String email, Long planId);
    int updateMoney(String email, long planId, int money);
    void exitGame(String email, Long planId, int transactionMoney);

}
