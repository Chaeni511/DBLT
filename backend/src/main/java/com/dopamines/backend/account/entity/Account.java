package com.dopamines.backend.account.entity;

import com.dopamines.backend.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false)
    private Long accountId;

//    @Column(nullable = false, unique = true)
    private String email;

//    @Column(nullable = false, length = 10, unique = true)
    private String nickname;

//    @Column(nullable = false, unique = true)
    private String kakaoId;

//    @Column(nullable = false)
    @Column(length = 2500)
    private String profile;

    private String profileMessage;
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
    private int accumulatedTime;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isDeleted;

    @ManyToMany
    private List<Role> roles = new ArrayList<>();

    private String refreshToken;

    public void updateRefreshToken(String newToken) {
        this.refreshToken = newToken;
    }
}
