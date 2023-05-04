package com.dopamines.backend.account.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.support.SpringFactoriesLoader;

@Getter
@Setter
public class SearchResponseDto {
    private String nickname;
    private String profile;
    private String profileMessage;
}
