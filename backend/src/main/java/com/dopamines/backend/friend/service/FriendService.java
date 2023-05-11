package com.dopamines.backend.friend.service;

import com.dopamines.backend.friend.dto.FriendResponseDto;

public interface FriendService {

    FriendResponseDto addFriend(String email, Long friendId);
    FriendResponseDto acceptFriend(String email, Long friendId);
    Boolean deleteFriend(Long friendId);
}

