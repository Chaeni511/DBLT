package com.dopamines.backend.friend.service;

import org.springframework.http.ResponseEntity;

public interface FriendService {

    void addFriend(String email, Long friendId);
    Boolean deleteFriend(Long friendId);
}

