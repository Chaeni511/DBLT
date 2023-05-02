package com.dopamines.backend.account.dto;

import lombok.Data;

@Data
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;

}
