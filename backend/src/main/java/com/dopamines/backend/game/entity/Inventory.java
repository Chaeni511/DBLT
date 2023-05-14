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
    @JoinColumn(name="item_id")
    private List<Item> inventory;


}
