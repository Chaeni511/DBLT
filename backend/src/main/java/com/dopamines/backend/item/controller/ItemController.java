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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemController {
    private final ItemService itemService;
    @GetMapping("/all")
    public ResponseEntity<Map<String, HashMap<String, List<ItemDto>>>> getItems(HttpServletRequest request) {
        return ResponseEntity.ok(itemService.getItems());
    }

}
