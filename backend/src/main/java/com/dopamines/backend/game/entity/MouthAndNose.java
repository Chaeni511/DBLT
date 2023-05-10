package com.dopamines.backend.game.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MouthAndNose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mouthAndNoseId;
    private int mouthAndNosePrice;
}
