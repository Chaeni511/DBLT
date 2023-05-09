package com.dopamines.backend.review.dto;

import lombok.*;

import java.time.LocalDate;

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

