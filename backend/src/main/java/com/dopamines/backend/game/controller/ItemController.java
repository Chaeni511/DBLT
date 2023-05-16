package com.dopamines.backend.game.controller;

import com.dopamines.backend.game.dto.InventoryDto;
import com.dopamines.backend.game.dto.ItemDto;
import com.dopamines.backend.game.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/buy")
//    public ResponseEntity<InventoryDto> buyItem(HttpServletRequest reqest, @RequestParam int item) {
//        String email = reqest.getRemoteUser();
//        return ResponseEntity.ok(itemService.buyItem(email, item));
//
//    }

}
