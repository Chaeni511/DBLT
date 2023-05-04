package com.dopamines.backend.account.service;

import com.dopamines.backend.account.dto.SearchResponseDto;
import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    @Override
    public Account editNickname(String email, String nickname) {
        log.info("AccountServiceImpl의 editNickname에서 찍는 nickname: " + nickname);

        // 닉네임 중복확인
        validateDuplicateNickname(nickname);

        Optional<Account> optional = accountRepository.findByEmail(email);
        Account account = null;

        if(optional.isEmpty()) {
            account = new Account();
            log.info("AccountServiceImpl의 editNickname에서");
        }else {
            account = optional.get();
            account.setNickname(nickname);
            accountRepository.save(account);
        }

        return account;
    }

    private void validateDuplicateNickname(String nickname) {

        if (accountRepository.existsByNickname(nickname)) {
            throw new RuntimeException("이미 존재하는 nickname입니다.");
        }
    }

    @Override
    public Account editProfileMessage(String email, String profileMessage) {
        log.info("AccountServiceImpl의 에서 찍는 profileMessage: " + profileMessage);

        Optional<Account> optional = accountRepository.findByEmail(email);
        Account account = null;

        if(optional.isEmpty()) {
            account = new Account();
            log.info("AccountServiceImpl의 profileMessage에서");

        }else {
            account = optional.get();
            account.setProfileMessage(profileMessage);
            accountRepository.save(account);
        }

        return account;
    }

    @Override
    public void deleteAccount(String email) {
        log.info("AccountServiceImpl의 에서 찍는 email: " + email);

        Optional<Account> optional = accountRepository.findByEmail(email);
        Account account = null;

        if(optional.isEmpty()) {
            account = new Account();
            log.info("AccountServiceImpl의 deleteAccount에서");

        }else {
            account = optional.get();
            account.setDeleted(true);
            account.setNickname(null);
            account.setProfile(null);
            account.setRefreshToken(null);
            account.setProfileMessage(null);
            account.setKakaoId(null);
//            account.setEmail(null);

            accountRepository.save(account);
        }
    }

    @Override
    public ArrayList<SearchResponseDto> searchNickname (String keyword) {
        ArrayList<SearchResponseDto> result = new ArrayList<SearchResponseDto>();

        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts){
            log.info("AccountServiceImpl: " + account);
            SearchResponseDto searchResponseDto = new SearchResponseDto();

            String nickname = account.getNickname();

            if(nickname.contains(keyword)) {
                searchResponseDto.setNickname(nickname);
                searchResponseDto.setProfile(account.getProfile());
                searchResponseDto.setProfileMessage(account.getProfileMessage());
                result.add(searchResponseDto);
            }

            return result;

        }

        return result;
    }
}
