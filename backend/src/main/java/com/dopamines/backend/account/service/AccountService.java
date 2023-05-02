package com.dopamines.backend.account.service;

import com.dopamines.backend.account.dto.AccountRequestDto;
import com.dopamines.backend.account.dto.RoleToUserRequestDto;

import java.util.Map;

/**
 * @author : Hunseong-Park
 * @date : 2022-07-04
 */
public interface AccountService {
    Long saveAccount(AccountRequestDto dto);
    Long saveRole(String roleName);
    Long addRoleToUser(RoleToUserRequestDto dto);

    void updateRefreshToken(String username, String refreshToken);

    Map<String, String> refresh(String refreshToken);
}
