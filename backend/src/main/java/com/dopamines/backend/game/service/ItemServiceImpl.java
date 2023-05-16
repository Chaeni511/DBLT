package com.dopamines.backend.game.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.game.dto.InventoryDto;
import com.dopamines.backend.game.dto.ItemDto;
import com.dopamines.backend.game.entity.Inventory;
import com.dopamines.backend.game.entity.Item;
import com.dopamines.backend.game.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final AccountRepository accountRepository;

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

    @Override
    public void buyItem(String email, int item){
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty()) {
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        } else {
            Inventory inventory = new Inventory();
//            inventory.set
        }

//        return inventoryDto;
    }
}
