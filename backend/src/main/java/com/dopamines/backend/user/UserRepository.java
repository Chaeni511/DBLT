package com.dopamines.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dopamines.backend.user.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

//    Long countByNicknameIs(String nickname);
//    Optional<User> findByNickname(String nickname);

}
