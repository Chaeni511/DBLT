package com.dopamines.backend.game.repository;

import com.dopamines.backend.game.entity.MyCharacter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MyCharacterRepository extends JpaRepository<MyCharacter, Long> {
    Character findByAccount_AccountId(Long id);
    Character findMyCharacterByAccount_AccountId(Long id);
}
