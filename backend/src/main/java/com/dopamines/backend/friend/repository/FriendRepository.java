package com.dopamines.backend.friend.repository;

import com.dopamines.backend.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository  extends JpaRepository<Friend, Long> {
}
