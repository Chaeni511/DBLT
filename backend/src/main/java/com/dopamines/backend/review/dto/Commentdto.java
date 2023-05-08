package com.dopamines.backend.review.dto;

import com.dopamines.backend.plan.repository.PlanRepository;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Commentdto {

    private Long commentId;
    private String nickName;
    private String content;
    private LocalDateTime updateTime;
}
