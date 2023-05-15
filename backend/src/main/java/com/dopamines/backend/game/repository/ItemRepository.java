package com.dopamines.backend.game.repository;

import com.dopamines.backend.game.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByCategory(String category);

}
