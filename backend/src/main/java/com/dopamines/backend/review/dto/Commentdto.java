package com.dopamines.backend.review.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commentdto {
    private Long commentId;
    private String nickName;
    private String profile;
    private String content;
    private LocalDateTime updateTime;
}
