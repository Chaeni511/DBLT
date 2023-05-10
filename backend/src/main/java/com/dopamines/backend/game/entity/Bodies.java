package com.dopamines.backend.game.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bodies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int bodyId;
    private int bodyPrice;
}
