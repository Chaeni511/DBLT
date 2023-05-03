package com.dopamines.backend.account.service;

import com.dopamines.backend.account.dto.AccountRequestDto;
import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    Account account;
    @Override
    public Account editNickname(String nickname) {
//        log.info("의 에서 찍는 : " + nickname);
        log.info("AccountServiceImpl의 editNickname에서 찍는 nickname: " + nickname);
//        log.info("AccountServiceImpl의 editNickname에서 찍는 user: " );

        // 닉네임 중복확인
        validateDuplicateNickname(nickname);

        account.setNickname(nickname);

        return accountRepository.getByNickname(nickname);
    }

    private void validateDuplicateNickname(String nickname) {

        if (accountRepository.existsByNickname(nickname)) {
            throw new RuntimeException("이미 존재하는 nickname입니다.");
        }
    }

    @Override
    public Account editProfileMessage(String profileMessage) {
        account.setProfileMessage(profileMessage);

        return accountRepository.getByNickname(profileMessage);
    }
}
