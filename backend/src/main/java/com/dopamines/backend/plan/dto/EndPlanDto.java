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
public class EndPlanDto {

    private Long planId;
    private String title;
    private LocalDate planDate;
    private LocalTime planTime;
    private String location;
    private Integer cost;
    private Integer status;
    private MyEndPlanDto myDetail;
    private List<EndPlanParticipantDto> EndPlanParticipantDto;

}
