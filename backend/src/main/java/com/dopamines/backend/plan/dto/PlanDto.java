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
public class PlanDto {
    private Integer planId;
    private String title;
    private String description;
    private LocalDate planDate;
    private LocalTime planTime;
    private String location;
    private Integer find;
    private Integer status;
    private List<ParticipantDto> participantList;
}
