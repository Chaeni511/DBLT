package com.dopamines.backend.item.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.item.entity.MyCharacter;
import com.dopamines.backend.item.repository.MyCharacterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MyCharacterServiceImpl implements MyCharacterService {
    private final MyCharacterRepository myCharacterRepository;
    private final AccountRepository accountRepository;
    @Override
    public MyCharacter getMyCharacter(String email){
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty()){
            log.info("CharacterServiceImpl의 getMyCharacter에서 account.isEmpty()");
            return null;
        } else{
            return myCharacterRepository.findByAccount(account.get());
        }
    }
}
