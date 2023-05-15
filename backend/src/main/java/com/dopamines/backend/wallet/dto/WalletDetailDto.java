package com.dopamines.backend.wallet.dto;

import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.PrimitiveIterator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletDetailDto {

    private String title;
    private LocalDateTime transactionTime;
    private Integer type; // 0: 충전, 1: 송금, 2: 약속으로 얻은 지각비, 3: 약속으로 잃은 지각비
    private Integer money;
}
