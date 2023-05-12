package com.dopamines.backend.friend.repository;

import com.dopamines.backend.friend.entity.WaitingFriend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitingFriendRepository extends JpaRepository<WaitingFriend, Long> {
    List<WaitingFriend> findAllByAccount_AccountId(Long accountId);
    List<WaitingFriend> findAllByFriendIdAndAccount_AccountId(Long FriendId, Long accountId);
}
