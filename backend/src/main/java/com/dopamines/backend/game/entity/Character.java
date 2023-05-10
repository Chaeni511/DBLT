package com.dopamines.backend.game.entity;

import com.dopamines.backend.account.entity.Account;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Character {
    @Id
    private Long characterId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;

//    @Column(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="body_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ColumnDefault("1")
    private Bodies body;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="body_part_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ColumnDefault("0")
    private BodyParts bodyPart;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="eye_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ColumnDefault("1")
    private Eyes eye;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="glove_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ColumnDefault("0")
    private Gloves glove;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mouth_and_nose_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ColumnDefault("1")
    private MouthAndNoses mouthAndNose;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tail_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ColumnDefault("0")
    private Tails tail;
}
