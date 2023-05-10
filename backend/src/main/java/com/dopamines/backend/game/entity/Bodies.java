package com.dopamines.backend.game.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bodies {
    @Id
    private int bodyId;
}
