package com.dopamines.backend.game.dto;


import com.dopamines.backend.game.entity.Inventory;
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
    private List<Inventory> inventory;
    private HashMap<String, List<ItemDto>> items;

//    private List<ItemDto> bodies;
//    private List<ItemDto> bodyParts;
//    private List<ItemDto> eyes;
//    private List<ItemDto> gloves;
//    private List<ItemDto> mouthAndNoses;
//    private List<ItemDto> tails;

}
