package com.dopamines.backend.item.service;

import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.item.dto.ItemDto;
import com.dopamines.backend.item.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService{
    private final AccountRepository accountRepository;
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
        log.info("ItemService의 getItems에서 찍는 itemDto: " + itemDto);
        return itemDto;
    }


}
