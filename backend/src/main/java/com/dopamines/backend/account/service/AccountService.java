package com.dopamines.backend.account.service;

import com.dopamines.backend.account.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountService {
//    Account editNickname(String user,String nickname);
    Account editNickname(String email, String nickname);
//    Optional<Account> editNickname(String nickname);

    Account editProfileMessage(String email, String profileMessage);
}