package com.dopamines.backend.user.service;

import com.dopamines.backend.user.UserRepository;
import com.dopamines.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository userRepository;

    public User findUserByEmail(String email) {
        Optional<User> option = userRepository.findByEmail(email);
        // 이메일이 존재하고 삭제되지 않은 유저일 때
        if(option.isPresent() && !option.get().isDeleted()) {
            // 해당 유저 정보 반환
            return option.get();
        }else {
            return null;
        }
    }
}
