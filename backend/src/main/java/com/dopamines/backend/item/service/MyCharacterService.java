package com.dopamines.backend.item.service;

import com.dopamines.backend.item.dto.MyCharacterDto;

public interface MyCharacterService {
    MyCharacterDto getMyCharacter(String email);

}
