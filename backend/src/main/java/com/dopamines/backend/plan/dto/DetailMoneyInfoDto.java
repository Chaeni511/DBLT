package com.dopamines.backend.plan.dto;

import java.util.List;

public class DetailMoneyInfoDto {
    private Long planId;
    private Integer totalPayment;
    private Integer laterCount;
    private Integer cost;
    private List<CheckLaterDto> participants;
}
