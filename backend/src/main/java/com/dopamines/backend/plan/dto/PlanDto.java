package com.dopamines.backend.plan.dto;

import com.dopamines.backend.user.dto.UserDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanDto {
    private Integer planId;
    private UserDto user;
    private String title;
    private String description;
    private LocalDate planDate;
    private LocalTime planTime;
    private String location;
    private Integer find;
    private Integer status;
}
