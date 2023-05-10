package com.dopamines.backend.game.repository;

import com.dopamines.backend.game.entity.Skin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkinRepository extends JpaRepository<Skin, Integer> {
}
