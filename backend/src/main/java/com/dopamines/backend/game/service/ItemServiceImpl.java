package com.dopamines.backend.game.service;

import com.dopamines.backend.account.entity.Account;
<<<<<<< HEAD
import com.dopamines.backend.game.dto.InventoryDto;
import com.dopamines.backend.game.dto.ItemDto;
import com.dopamines.backend.game.dto.ShopResponseDto;
=======
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.game.dto.InventoryDto;
import com.dopamines.backend.game.dto.ItemDto;
>>>>>>> abd5e89ce4cf86f871226bdd5a0ee8e23fee5c51
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
    private final MyCharacterService myCharacterService;
    private final AccountRepository accountRepository;

    @Override
    public HashMap<String, List<ItemDto>> getItems(String email){
//    public Map<String, HashMap<String, List<ItemDto>>> getItems(String email){
        HashMap<String, List<ItemDto>> res = new HashMap<>();
//        Map<String, HashMap<String, List<ItemDto>>> res = new HashMap<>();
//        res.put("items", new HashMap<String, List<ItemDto>>());

        res.put("Eyes", toItemList(itemRepository.findByCategory("eyes"), email));
        res.put("Bodies", toItemList(itemRepository.findByCategory("bodies"), email));
        res.put("BodyParts", toItemList(itemRepository.findByCategory("body_parts"), email));
        res.put("MouthAndNoses", toItemList(itemRepository.findByCategory("mouth_and_noses"), email));
        res.put("Gloves", toItemList(itemRepository.findByCategory("gloves"), email));
        res.put("Tails", toItemList(itemRepository.findByCategory("tails"), email));

        return res;
    }

    private List<ItemDto> toItemList(List<Item> items, String email) {
        List<Inventory> inventory = inventoryRepository.findAllByAccount_Email(email);
//        log.info("toItemList의 inventory: "+ inventory.get(0).toString());
        List<Item> itemIdList = inventoryListToItemIdList(inventory);
        MyCharacter myCharacter = myCharacterRepository.findMyCharacterByAccount_Email(email);
        List<ItemDto> list = new ArrayList<ItemDto>();
        for(Item item : items){
            ItemDto itemDto = new ItemDto();
            itemDto.setCode(item.getCode());
            itemDto.setPrice(item.getPrice());
            itemDto.setItemId(item.getItemId());

            if(itemIdList.contains(item)){
                itemDto.setBought(true);
            } else {
                itemDto.setBought(false);
            }

            if(item.getCategory().equals("bodies")){
                if (item.getCode() == myCharacter.getBody()) {
                    itemDto.setWorn(true);
                } else {
                    itemDto.setWorn(false);
                }
            } else if(item.getCategory().equals("body_parts")){
                if (item.getCode() == myCharacter.getBodyPart()) {
                    itemDto.setWorn(true);
                } else {
                    itemDto.setWorn(false);
                }
            } else if(item.getCategory().equals("eyes")){
                if (item.getCode() == myCharacter.getEye()) {
                    itemDto.setWorn(true);
                } else {
                    itemDto.setWorn(false);
                }
            } else if(item.getCategory().equals("gloves")){
                if (item.getCode() == myCharacter.getGloves()) {
                    itemDto.setWorn(true);
                } else {
                    itemDto.setWorn(false);
                }
            } else if(item.getCategory().equals("mouth_and_noses")){
                if (item.getCode() == myCharacter.getMouthAndNose()) {
                    itemDto.setWorn(true);
                } else {
                    itemDto.setWorn(false);
                }
            } else if(item.getCategory().equals("tails")){
                if (item.getCode() == myCharacter.getTail()) {
                    itemDto.setWorn(true);
                } else {
                    itemDto.setWorn(false);
                }
            } else {
                log.info("category가 이상한데.. " + item.getCategory());
            }

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

    @Override
    public ShopResponseDto getShop(String email) {
        Account account = new Account();
        HashMap<String, List<ItemDto>> items = getItems(email);
        ShopResponseDto shopResponseDto = new ShopResponseDto();
        shopResponseDto.setMyCharacter(myCharacterService.getMyCharacter(email));
        shopResponseDto.setItems(items);
        return shopResponseDto;
    };

    private List<Item> inventoryListToItemIdList(List<Inventory> inventoryList) {
        List<Item> itemIdList = new ArrayList<Item>();
        for(Inventory inventory : inventoryList) {
            itemIdList.add(inventory.getItem());
        }
        return itemIdList;
    }


}
