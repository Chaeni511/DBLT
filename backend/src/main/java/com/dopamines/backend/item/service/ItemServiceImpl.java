package com.dopamines.backend.item.service;

import com.dopamines.backend.item.dto.ItemDto;
import com.dopamines.backend.item.entity.Item;
import com.dopamines.backend.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Override
    public Map<String, HashMap<String, List<ItemDto>>> getItems(){
        Map<String, HashMap<String, List<ItemDto>>> res = new HashMap<>();
        res.put("items", new HashMap<String, List<ItemDto>>());

        res.get("items").put("Eyes", toItemList(itemRepository.findByCategory("eyes")));
        res.get("items").put("Bodies", toItemList(itemRepository.findByCategory("bodies")));
        res.get("items").put("BodyParts", toItemList(itemRepository.findByCategory("body_parts")));
        res.get("items").put("MouthAndNoses", toItemList(itemRepository.findByCategory("mouth_and_noses")));
        res.get("items").put("Gloves", toItemList(itemRepository.findByCategory("gloves")));
        res.get("items").put("Tails", toItemList(itemRepository.findByCategory("tails")));

        return res;
    }

    private List<ItemDto> toItemList(List<Item> items) {
        List<ItemDto> list = new ArrayList<ItemDto>();
        for(Item item : items){
            ItemDto itemDto = new ItemDto();
            itemDto.setCode(item.getCode());
            itemDto.setPrice(item.getPrice());
            list.add(itemDto);
        }
        return list;
    }
}
