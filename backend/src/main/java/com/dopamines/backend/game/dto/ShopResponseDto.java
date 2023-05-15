package com.dopamines.backend.game.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopResponseDto {
    private MyCharacterDto myCharacterDto;
    private InventoryDto inventoryDto;
    private List<ItemDto> bodies;
    private List<ItemDto> bodyParts;
    private List<ItemDto> eyes;
    private List<ItemDto> gloves;
    private List<ItemDto> mouthAndNoses;
    private List<ItemDto> tails;

}
