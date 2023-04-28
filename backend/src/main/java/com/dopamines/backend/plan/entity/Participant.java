package com.dopamines.backend.plan.entity;


import com.dopamines.backend.user.entity.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Integer participantId;

    // 약속
    @ManyToOne(fetch = FetchType.LAZY)  // 1:N
    @JoinColumn(name="plan_id") //Join 기준
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Plan plan;
    // 참여자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "is_arrived", nullable = false)
    private boolean isArrived;

    @Column(name = "arrival_dt")
    private LocalDateTime arrivalDt;

    @Column(name = "late_time")
    private Long lateTime;
}
