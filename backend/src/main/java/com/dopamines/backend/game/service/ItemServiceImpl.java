package com.dopamines.backend.game.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.game.dto.InventoryDto;
import com.dopamines.backend.game.dto.ItemDto;
import com.dopamines.backend.game.dto.ShopResponseDto;
import com.dopamines.backend.game.entity.Inventory;
import com.dopamines.backend.game.entity.Item;
import com.dopamines.backend.game.entity.MyCharacter;
import com.dopamines.backend.game.repository.InventoryRepository;
import com.dopamines.backend.game.repository.ItemRepository;
import com.dopamines.backend.game.repository.MyCharacterRepository;
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
    private final InventoryRepository inventoryRepository;
    private final MyCharacterRepository myCharacterRepository;

    @Override
    public Map<String, HashMap<String, List<ItemDto>>> getItems(String email){
        Map<String, HashMap<String, List<ItemDto>>> res = new HashMap<>();
        res.put("items", new HashMap<String, List<ItemDto>>());

        res.get("items").put("Eyes", toItemList(itemRepository.findByCategory("eyes"), email));
        res.get("items").put("Bodies", toItemList(itemRepository.findByCategory("bodies"), email));
        res.get("items").put("BodyParts", toItemList(itemRepository.findByCategory("body_parts"), email));
        res.get("items").put("MouthAndNoses", toItemList(itemRepository.findByCategory("mouth_and_noses"), email));
        res.get("items").put("Gloves", toItemList(itemRepository.findByCategory("gloves"), email));
        res.get("items").put("Tails", toItemList(itemRepository.findByCategory("tails"), email));

        return res;
    }

    private List<ItemDto> toItemList(List<Item> items, String email) {
        List<Inventory> inventory = inventoryRepository.findAllByAccount_Email(email);
//        log.info("toItemList의 inventory: "+ inventory.get(0).toString());
        MyCharacter myCharacter = myCharacterRepository.findMyCharacterByAccount_Email(email);
        List<ItemDto> list = new ArrayList<ItemDto>();
        for(Item item : items){
            ItemDto itemDto = new ItemDto();
            itemDto.setCode(item.getCode());
            itemDto.setPrice(item.getPrice());

            if(inventory.contains(item)){
                itemDto.setBought(true);
            } else {
                itemDto.setBought(false);
            }

            if(item.getItemId() == myCharacter.getBody()
                    || item.getItemId() == myCharacter.getTail()
                    || item.getItemId() == myCharacter.getEye()
                    || item.getItemId() == myCharacter.getGloves()
                    || item.getItemId() == myCharacter.getBodyPart()
                    || item.getItemId() == myCharacter.getMouthAndNose()
            ) {
                itemDto.setWorn(true);
            } else {
                itemDto.setWorn(false);
            }

            list.add(itemDto);
        }
        return list;
    }

    @Override
    public InventoryDto buyItem(String email, int item){
        InventoryDto inventoryDto = new InventoryDto();
        return inventoryDto;
    }

    @Override
    public ShopResponseDto getShop(String email) {
        Account account = new Account();

        ShopResponseDto shopResponseDto = new ShopResponseDto();

        return shopResponseDto;
    };

}
