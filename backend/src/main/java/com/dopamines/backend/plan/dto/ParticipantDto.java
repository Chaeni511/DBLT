package com.dopamines.backend.plan.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantDto {

    private Integer userId;
    private String nickname;
    private String profile;
    private Boolean isHost;
    private Boolean isArrived;
    private Integer designation; // 0 보통, 1 일찍, 2 지각

}