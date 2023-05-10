package com.dopamines.backend.game.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.game.entity.Character;
import com.dopamines.backend.game.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService{
    private final CharacterRepository characterRepository;
    private final AccountRepository accountRepository;
    @Override
    public Character getMyCharacter(String email){
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty()){
            log.info("CharacterServiceImpl의 getMyCharacter에서 account.isEmpty()");
            return null;
        } else{
//            log.info("findByAccount_AccountId: " + characterRepository.findByAccount_AccountId(account.get().getAccountId()).toString());
            log.info("findCharacterByAccount_AccountId: " + characterRepository.findCharacterByAccount_AccountId(account.get().getAccountId()).toString());

            return characterRepository.findCharacterByAccount_AccountId(account.get().getAccountId());
        }
    }
}