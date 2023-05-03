package com.dopamines.backend.plan.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlanListDto {
    private Integer planId;
    private String title;
    private LocalDate planDate;
    private LocalTime planTime;
    private String location;
    private Integer find;
    private Long diffDay;
    private Integer participantCount;
    private Integer status;
    private List<ParticipantDto> participantList;

}
