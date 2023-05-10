package com.dopamines.backend.item.controller;

import com.dopamines.backend.item.dto.ItemDto;
import com.dopamines.backend.item.service.ItemService;
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
@RequestMapping("/item")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemController {
    private final ItemService itemService;
    @GetMapping("/items")
    public ResponseEntity<ItemDto> getItems(HttpServletRequest request) {
        return ResponseEntity.ok(itemService.getItems());
    }

}
