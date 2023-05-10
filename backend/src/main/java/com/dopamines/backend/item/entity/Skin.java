package com.dopamines.backend.item.entity;

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
public class Skin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int skinId;

    private int skinPrice;

}
