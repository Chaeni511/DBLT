package com.dopamines.backend.game.service;

import com.dopamines.backend.game.dto.ItemDto;

import javax.servlet.http.HttpServletRequest;

public interface ItemService {
    ItemDto getItems();
}
