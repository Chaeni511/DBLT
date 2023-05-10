package com.dopamines.backend.game.repository;

import com.dopamines.backend.game.entity.Tail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TailRepository extends JpaRepository<Tail, Integer> {
}
