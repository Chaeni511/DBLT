package com.dopamines.backend.account.service;

import com.dopamines.backend.account.dto.SearchResponseDto;
import com.dopamines.backend.account.entity.Account;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccountService {
    Account editNickname(String email, String nickname);
//    Optional<Account> editNickname(String nickname);

    Account editProfileMessage(String email, String profileMessage);

    Account editProfile(String email, MultipartFile file);

    void deleteAccount(String email);

    List<SearchResponseDto> searchNickname(String keyword);


}
