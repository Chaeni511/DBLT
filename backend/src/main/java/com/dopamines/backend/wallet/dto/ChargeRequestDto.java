package com.dopamines.backend.wallet.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeRequestDto {
    private int money;
    private String method;
    private LocalDateTime transactionTime;
    private String receipt;

}
