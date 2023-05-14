package com.dopamines.backend.game.service;

import com.dopamines.backend.game.dto.InventoryDto;
import com.dopamines.backend.game.dto.ItemDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ItemService {

    Map<String, HashMap<String, List<ItemDto>>> getItems();
    InventoryDto buyItem(String email, int item);
}
