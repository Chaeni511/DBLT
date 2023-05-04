package com.dopamines.backend.plan.dto;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class MyEndPlanDto {

    private Long accountId;
    private String nickname;
    private String profile;
    private Integer designation; // 0 보통, 1 일찍, 2 지각
    private LocalTime arrivalTime;
    private Long lateTime; // >=0 지각안함, <0 지각
    private Integer getMoney; // 획득 + , 지출 - 한 돈


}
