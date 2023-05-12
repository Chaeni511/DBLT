package com.dopamines.backend.item.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyCharacterDto {

    private int bodies;
    private int bodyParts;
    private int eyes;
    private int gloves;
    private int mouthAndGloves;
    private int tail;


}
