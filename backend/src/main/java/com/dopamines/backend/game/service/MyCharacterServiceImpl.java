package com.dopamines.backend.game.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.game.dto.MyCharacterDto;
import com.dopamines.backend.game.entity.Inventory;
import com.dopamines.backend.game.entity.MyCharacter;
import com.dopamines.backend.game.repository.InventoryRepository;
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

    @Override
    public MyCharacterDto getMyCharacter(String email){
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty()){
            log.info("CharacterServiceImpl의 getMyCharacter에서 account.isEmpty()");
            return null;
        } else{
            MyCharacter myCharacter = myCharacterRepository.findByAccount(account.get());
            MyCharacterDto myCharacterDto = new MyCharacterDto(
                    myCharacter.getBody(),
                    myCharacter.getBodyPart(),
                    myCharacter.getEye(),
                    myCharacter.getGloves(),
                    myCharacter.getMouthAndNose(),
                    myCharacter.getTail()
            );

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
