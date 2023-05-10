package com.dopamines.backend.game.repository;

import com.dopamines.backend.game.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CharacterRepository extends JpaRepository<Character, Long> {
    Character findByAccount_AccountId(Long id);
    Character findCharacterByAccount_AccountId(Long id);
}
