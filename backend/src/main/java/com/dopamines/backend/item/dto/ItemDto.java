package com.dopamines.backend.item.dto;

import com.dopamines.backend.item.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemDto {
    private List<Skin> skins;
    private List<Body> bodies;
    private List<Eye> eyes;
    private List<Gloves> gloves;
    private List<MouthAndNose> mouthAndNoses;
    private List<Tail> tails;

}
