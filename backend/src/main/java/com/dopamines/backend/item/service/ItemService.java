package com.dopamines.backend.item.service;

import com.dopamines.backend.item.dto.ItemDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ItemService {

    Map<String, HashMap<String, List<ItemDto>>> getItems();

}
