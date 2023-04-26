package com.dopamines.backend.user.entity;

import com.dopamines.backend.common.BaseTimeEntity;

import javax.persistence.*;

import com.dopamines.backend.user.Role;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Id;

@Entity(name="user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

//    @Column(nullable = false)
//    private String name;

    @Column(nullable = false, length = 10, unique = true)
    private String nickname;

//    @Column(nullable = false)
    private String profile;

//    @Column(nullable = false)
//    private String address;

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

    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    private User(String nickname, String email, String profile, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.profile = profile;
        this.thyme = 0;
        this.totalIn = 0;
        this.totalOut = 0;
        this.arrivalTime = 0;
        this.isDeleted = false;
        this.role = role;

    }
    public User update(String nickname, String profile) {
        this.nickname = nickname;
        this.profile = profile;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
