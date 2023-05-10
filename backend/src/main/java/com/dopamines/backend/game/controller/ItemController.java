package com.dopamines.backend.game.controller;

import com.dopamines.backend.game.dto.ItemDto;
import com.dopamines.backend.game.service.ItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemController {
    private final ItemService itemService;
    @GetMapping("/items")
    public ResponseEntity<ItemDto> getItems(HttpServletRequest request) {
        return ResponseEntity.ok(itemService.getItems());
    }

}
