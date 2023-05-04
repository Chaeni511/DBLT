package com.dopamines.backend.account.repository;

import com.dopamines.backend.account.dto.SearchResponseDto;
import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.service.AccountService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
    Optional<Account> getByNickname(String nickname);

    Optional<Account> findByNickname(String nickname);
    Optional<Account> findById(Long accountId);

//    @Query("SELECT nickname, profile, profileMessage FROM Account WHERE nickname LIKE %:keyword%")
    List<SearchResponseDto> findByNicknameContaining(@Param("keyword") String keyword);
}
