package com.dopamines.backend.account.dto;

import com.dopamines.backend.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDto {
    private String username;
    private String password;

    public Account toEntity() {
        return Account.builder()
                .username(username)
                .password(password)
                .build();
    }

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
