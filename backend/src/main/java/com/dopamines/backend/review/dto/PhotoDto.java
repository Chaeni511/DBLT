package com.dopamines.backend.review.dto;

import com.dopamines.backend.plan.entity.Plan;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoDto {

    private Long photoId;
    private Long planId;
    private String photoUrl;
    private LocalDate planDate;
}

