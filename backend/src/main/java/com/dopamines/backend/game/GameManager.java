package com.dopamines.backend.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
public class GameManager {
    private Map<Long, Integer> remainMoney = new HashMap<>();

    public void setGameMoney(long planId, int money) {
        if(remainMoney.containsKey(planId)) return;
        remainMoney.put(planId, money);
    }
    public void subGameMoney(long planId, int money) {
        remainMoney.put(planId, remainMoney.get(planId) - money);
        if(remainMoney.get(planId) <= 0) {
            remainMoney.remove(planId);
        }
    }
    public int getGameMoney(long planId) {
        if(!remainMoney.containsKey(planId)) return 0;
        return remainMoney.get(planId);
    }
}
