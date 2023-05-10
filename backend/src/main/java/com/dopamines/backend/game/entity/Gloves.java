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
public class Gloves {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gloveId;
    private int glovePrice;
}
