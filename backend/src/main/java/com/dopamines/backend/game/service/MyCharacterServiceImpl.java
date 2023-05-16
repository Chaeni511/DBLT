package com.dopamines.backend.game.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.game.dto.MyCharacterDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MyCharacterServiceImpl implements MyCharacterService {
    private final MyCharacterRepository myCharacterRepository;
    private final AccountRepository accountRepository;
    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public MyCharacterDto getMyCharacter(String email){
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty()){
            log.info("CharacterServiceImpl의 getMyCharacter에서 account.isEmpty()");
            return null;
        } else{
            MyCharacter myCharacter = myCharacterRepository.findByAccount(account.get());
            log.info("eye" +myCharacter.getEye());
            log.info("tail" +myCharacter.getTail());
            log.info("body" +myCharacter.getBody());
            log.info("bodyPart" +myCharacter.getBodyPart());
            log.info("gloves" +myCharacter.getGloves());
            log.info("mouth&nose" +myCharacter.getMouthAndNose());
            MyCharacterDto myCharacterDto = new MyCharacterDto();

            Optional<Item> body = itemRepository.findByItemId(myCharacter.getBody());
            Optional<Item> bodyPart = itemRepository.findByItemId(myCharacter.getBodyPart());
            Optional<Item> eye = itemRepository.findByItemId(myCharacter.getEye());
            Optional<Item> gloves = itemRepository.findByItemId(myCharacter.getGloves());
            Optional<Item> mouthAndNose = itemRepository.findByItemId(myCharacter.getMouthAndNose());
            Optional<Item> tail = itemRepository.findByItemId(myCharacter.getTail());

            if(body.isEmpty()) {
                myCharacterDto.setBodies(0);
            } else {
                myCharacterDto.setBodies(body.get().getCode());
            }

            if(bodyPart.isEmpty()) {
                myCharacterDto.setBodyParts(0);
            } else {
                myCharacterDto.setBodyParts(bodyPart.get().getCode());
            }

            if(eye.isEmpty()) {
                myCharacterDto.setEyes(0);
            } else {
                myCharacterDto.setEyes(eye.get().getCode());
            }

            if(gloves.isEmpty()) {
                myCharacterDto.setGloves(0);
            } else {
                myCharacterDto.setGloves(gloves.get().getCode());
            }

            if(mouthAndNose.isEmpty()) {
                myCharacterDto.setMouthAndNoses(0);
            } else {
                myCharacterDto.setMouthAndNoses(mouthAndNose.get().getCode());
            }

            if(tail.isEmpty()) {
                myCharacterDto.setTails(0);
            } else {
                myCharacterDto.setTails(tail.get().getCode());
            }



            return myCharacterDto;

        }
    }

    @Override
    public void wearItem(String email, MyCharacterDto myCharacterDto){
        Optional<Account> account = accountRepository.findByEmail(email);
        List<Inventory> inventoryList = inventoryRepository.findAllByAccount(account.get());
        for(Inventory i : inventoryList) {
            log.info("inventoryList: " + i.getItem().getCategory()+ "/" + i.getItem().getCode());
        }
        List<Integer> itemIdList = inventoryListToItemIdList(inventoryList);
        for(Integer i : itemIdList) {
            log.info("itemIdList: " + i);
        }
        MyCharacter myCharacter = myCharacterRepository.findMyCharacterByAccount_Email(email);
        if (
                itemIdList.contains(myCharacterDto.getBodies())
                && itemIdList.contains(myCharacterDto.getGloves())
                && itemIdList.contains(myCharacterDto.getEyes())
                && itemIdList.contains(myCharacterDto.getBodyParts())
                && itemIdList.contains(myCharacterDto.getMouthAndNoses())
                && itemIdList.contains(myCharacterDto.getTails())
        ) {
            myCharacter.setBody(myCharacterDto.getBodies());
            myCharacter.setEye(myCharacterDto.getEyes());
            myCharacter.setGloves(myCharacterDto.getGloves());
            myCharacter.setTail(myCharacterDto.getTails());
            myCharacter.setBodyPart(myCharacterDto.getBodyParts());
            myCharacter.setMouthAndNose(myCharacterDto.getMouthAndNoses());
            myCharacterRepository.save(myCharacter);
        } else {
            log.info("myCharacterDto.getBodies()"+ itemIdList.contains(myCharacterDto.getBodies()));
            log.info("myCharacterDto.getGloves()"+ itemIdList.contains(myCharacterDto.getGloves()));
            log.info("myCharacterDto.getEyes()"+ itemIdList.contains(myCharacterDto.getEyes()));
            log.info("myCharacterDto.getBodyParts()"+ itemIdList.contains(myCharacterDto.getBodyParts()));
            log.info("myCharacterDto.getMouthAndNoses()"+ itemIdList.contains(myCharacterDto.getMouthAndNoses()));
            log.info("myCharacterDto.getTails()"+ itemIdList.contains(myCharacterDto.getTails()));
            throw new IllegalArgumentException("구매하지 않은 아이템이 포함되어 있습니다.");
        }
    }

    private List<Integer> inventoryListToItemIdList(List<Inventory> inventoryList) {
        List<Integer> itemIdList = new ArrayList<Integer>();
        for(Inventory inventory : inventoryList) {
            itemIdList.add(inventory.getItem().getItemId());
        }
        return itemIdList;
    }
}
