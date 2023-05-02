package com.dopamines.backend.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Integer userId;

    private String email;

    private String nickname;

    private String profile;

    private String address;

    private Integer thyme;

    private Integer totalIn;

    private Integer totalOut;

    private Integer arrivalTime;

    private Boolean isDeleted;

}