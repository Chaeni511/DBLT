package com.dopamines.backend.user.repository;

import com.dopamines.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByNickname(String nickname);
    Optional<User> findById(Integer userId);
    Optional<User> findByEmail(String email);

}
