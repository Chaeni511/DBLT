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
    private final ItemService itemService;
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
        List<Inventory> inventoryList = inventoryRepository.findAllByAccount_Email(email);
        List<Integer> itemIdList = itemService.inventoryListToItemIdList(inventoryList);
        MyCharacter myCharacter = myCharacterRepository.findMyCharacterByAccount_Email(email);
        if (
                itemIdList.contains(myCharacterDto.getBodies())
                || itemIdList.contains(myCharacterDto.getEyes())
                || itemIdList.contains(myCharacterDto.getGloves())
                || itemIdList.contains(myCharacterDto.getTails())
                || itemIdList.contains(myCharacterDto.getBodyParts())
                || itemIdList.contains(myCharacterDto.getMouthAndNoses())
        ) {
            myCharacter.setBody(myCharacterDto.getBodies());
            myCharacter.setEye(myCharacterDto.getEyes());
            myCharacter.setGloves(myCharacterDto.getGloves());
            myCharacter.setTail(myCharacterDto.getTails());
            myCharacter.setBodyPart(myCharacterDto.getBodyParts());
            myCharacter.setMouthAndNose(myCharacterDto.getMouthAndNoses());
            myCharacterRepository.save(myCharacter);
        } else {
            throw new IllegalArgumentException("구매하지 않은 아이템이 포함되어 있습니다.");
        }
    }
}
