package com.dopamines.backend.game.service;

import com.dopamines.backend.game.dto.ItemDto;
import com.dopamines.backend.game.entity.Body;
import com.dopamines.backend.game.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    BodyRepository bodyRepository;
    @Autowired
    EyeRepository eyeRepository;
    @Autowired
    GlovesRepository glovesRepository;
    @Autowired
    MouthAndNoseRepository mouthAndNoseRepository;
    @Autowired
    SkinRepository skinRepository;
    @Autowired
    TailRepository tailRepository;

    @Override
    public ItemDto getItems(){
        ItemDto itemDto = new ItemDto();
        itemDto.setBodies(bodyRepository.findAll());
        itemDto.setEyes(eyeRepository.findAll());
        itemDto.setGloves(glovesRepository.findAll());
        itemDto.setSkins(skinRepository.findAll());
        itemDto.setTails(tailRepository.findAll());
        itemDto.setMouthAndNoses(mouthAndNoseRepository.findAll());
        log.info("ItemService의 getItems에서 찍는 itemDto: " + itemDto.toString());
        return itemDto;
    }
}
