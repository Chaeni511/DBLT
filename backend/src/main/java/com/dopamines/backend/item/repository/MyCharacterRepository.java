package com.dopamines.backend.item.repository;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.item.entity.MyCharacter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MyCharacterRepository extends JpaRepository<MyCharacter, Long> {
    MyCharacter findByAccount(Account account);
    MyCharacter findMyCharacterByAccount_AccountId(Long id);
}
