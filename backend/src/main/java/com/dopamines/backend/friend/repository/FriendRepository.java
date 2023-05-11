package com.dopamines.backend.friend.repository;

import com.dopamines.backend.friend.entity.Friend;
import com.dopamines.backend.friend.entity.WaitingFriend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository  extends JpaRepository<Friend, Long> {
    List<Friend> findAllByAccount_AccountId(Long accountId);
    Friend findAllById(Long id);
}
