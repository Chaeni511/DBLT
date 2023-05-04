package com.dopamines.backend.plan.dto;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class EndPlanParticipantDto {

    private Long accountId;
    private String nickname;
    private String profile;
    private Long lateTime;
    private Integer designation; // 0 보통, 1 일찍, 2 지각
}
