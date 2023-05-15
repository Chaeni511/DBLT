package com.dopamines.backend.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    private int code;
    private int price;
    private boolean bought;
    private boolean worn;

}
