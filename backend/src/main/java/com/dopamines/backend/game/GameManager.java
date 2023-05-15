package com.dopamines.backend.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
public class GameManager {
    private Map<Long, Integer> remainMoney;

    public void setGameMoney(long planId, int money) {
        remainMoney.put(planId, money);
    }
    public void subGameMoney(long planId, int money) {
        remainMoney.put(planId, remainMoney.get(planId) - money);
        if(remainMoney.get(planId) <= 0) {
            remainMoney.remove(planId);
        }
    }
    public int getGameMoney(long planId) {
        return remainMoney.get(planId);
    }
}
