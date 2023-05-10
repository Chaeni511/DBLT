package com.dopamines.backend.game.service;

import com.dopamines.backend.game.entity.Character;

public interface CharacterService {
    Character getMyCharacter(String email);
}
