package com.dopamines.backend.plan.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanListDto {
    private Long planId;
    private String title;
    private LocalDate planDate;
    private LocalTime planTime;
    private String location;
    private Long diffHours;
    private Long diffMinutes;
    private Integer participantCount;
    private Integer status;
    private List<PlanListParticipantDto> participantList;


}
