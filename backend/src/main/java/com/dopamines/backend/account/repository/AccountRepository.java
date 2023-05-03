package com.dopamines.backend.account.repository;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.service.AccountService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
    Account getByNickname(String nickname);

}
