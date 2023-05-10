package com.dopamines.backend.game.entity;

import com.dopamines.backend.account.entity.Account;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    private Account account;

//    @Column(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="skin_id")
    @ColumnDefault("1")
    private Skin skin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="body_id")
    @ColumnDefault("0")
    private Body body;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="eye_id")
    @ColumnDefault("1")
    private Eye eye;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="glove_id")
    @ColumnDefault("0")
    private Gloves glove;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mouth_and_nose_id")
    @ColumnDefault("1")
    private MouthAndNose mouthAndNose;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tail_id")
    @ColumnDefault("0")
    private Tail tail;
}
