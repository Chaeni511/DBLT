package com.dopamines.backend.friend.repository;

import com.dopamines.backend.friend.entity.WaitingFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingFriendRepository extends JpaRepository<WaitingFriend, Long> {
}
