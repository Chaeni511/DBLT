package com.dopamines.backend.game.entity;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

public class Inventory {
    @Id
    private int inventoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="skin_id")
    private List<Skins> skins;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="body_id")
    private List<Bodies> bodies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="eye_id")
    private List<Eyes> eyes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="glove_id")
    private List<Gloves> gloves;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mouth_and_nose_id")
    private List<MouthAndNoses> mouthAndNoses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tail_id")
    private List<Tails> tails;








}
