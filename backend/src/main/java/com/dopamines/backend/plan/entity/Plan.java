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
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Integer planId;

    // 방장
    @ManyToOne(fetch = FetchType.LAZY)  // 1:N
    @JoinColumn(name="user_id") //Join 기준
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "plan_dt")
    private LocalDateTime planDt;

    @Column(name="location")
    private String location;

    @Column(name = "find")
    private Integer find;

    @Column(name="status")
    private Integer status;

}
