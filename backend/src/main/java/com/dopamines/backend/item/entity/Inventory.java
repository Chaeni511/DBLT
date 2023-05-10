package com.dopamines.backend.item.entity;

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
    private List<Skin> skins;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="body_id")
    private List<Body> bodies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="eye_id")
    private List<Eye> eyes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="glove_id")
    private List<Gloves> gloves;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mouth_and_nose_id")
    private List<MouthAndNose> mouthAndNoses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tail_id")
    private List<Tail> tails;

}
