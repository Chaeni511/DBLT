package com.dopamines.backend.friend.service;

import com.dopamines.backend.friend.dto.FriendResponseDto;
import com.dopamines.backend.friend.entity.Friend;

import java.util.List;

public interface FriendService {
    List<Friend> getFriendList(String email);
    FriendResponseDto addFriend(String email, Long friendId);
    FriendResponseDto acceptFriend(String email, Long friendId);
    FriendResponseDto denyFriend(String email, Long friendId);
    FriendResponseDto deleteFriend(String email, Long friendId);
}

