package com.dopamines.backend.plan.entity;

import com.dopamines.backend.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Integer planId;

    // 방장
    @ManyToOne(fetch = FetchType.LAZY)  // 1:N
    @JoinColumn(name="user_id") //Join 기준
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "plan_date")
    private LocalDate planDate;

    @Column(name = "plan_time")
    private LocalTime planTime;

    @Column(name="location")
    private String location;

    @Column(name = "find")
    private Integer find;

    @Column(name="status")
    private Integer status; // 0: 기본, 1: 위치공유(30분 전~약속시간), 2: 게임 활성화(약속시간~1시간 후), 3: 약속 종료(1시간 이후)

}
