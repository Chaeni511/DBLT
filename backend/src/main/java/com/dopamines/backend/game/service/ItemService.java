package com.dopamines.backend.game.service;

import com.dopamines.backend.game.dto.ItemDto;
import com.dopamines.backend.game.dto.ShopResponseDto;

import java.util.HashMap;
import java.util.List;

public interface ItemService {

    HashMap<String, List<ItemDto>>  getItems(String email);
    void buyItem(String email, int item);
    ShopResponseDto getShop(String email);
}
