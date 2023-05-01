package com.dopamines.backend.user.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile", length = 2500)
    private String profile;

    @Column(name = "address")
    private String address;

    // 게임머니
    @Column(name = "thyme")
    private Integer thyme;

    @Column(name = "total_in")
    private Integer totalIn;

    @Column(name = "total_out")
    private Integer totalOut;

    // 누적 도착 시간
    @Column(name = "arrival_time")
    private Integer arrivalTime;

    // 탈퇴여부
    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
