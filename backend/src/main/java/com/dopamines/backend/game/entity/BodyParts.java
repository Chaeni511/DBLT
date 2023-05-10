package com.dopamines.backend.game.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BodyParts {
    @Id
    private int bodyPartId;
}
