package com.dopamines.backend.user.entity;

import com.dopamines.backend.common.BaseTimeEntity;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Id;

@Entity(name="user")
@Getter
@Builder
@NoArgsConstructor
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String profile;

//    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int thyme;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int totalIn;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int totalOut;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int arrivalTime;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isDeleted;
}
