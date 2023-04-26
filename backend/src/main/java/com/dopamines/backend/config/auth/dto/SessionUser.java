package com.dopamines.backend.config.auth.dto;

import com.dopamines.backend.user.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String nickname;
    private String email;
    private String profile;

    public SessionUser(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profile = user.getProfile();
    }
}
