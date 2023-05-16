package com.dopamines.backend.game.service;

import com.dopamines.backend.game.dto.InventoryDto;
import com.dopamines.backend.game.dto.ItemDto;
import com.dopamines.backend.game.dto.ShopResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ItemService {

    HashMap<String, List<ItemDto>> getItems(String email);
    InventoryDto buyItem(String email, int item);
    ShopResponseDto getShop(String email);
}
