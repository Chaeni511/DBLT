package com.dopamines.backend.game.dto;


import lombok.*;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopResponseDto {
    private MyCharacterDto myCharacter;
//    private InventoryDto inventory;
    private HashMap<String, List<ItemDto>> items;
}
