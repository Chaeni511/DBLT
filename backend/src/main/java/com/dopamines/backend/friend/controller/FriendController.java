package com.dopamines.backend.friend.controller;

import com.dopamines.backend.friend.dto.FriendResponseDto;
import com.dopamines.backend.friend.service.FriendService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "friend", description = "친구 신청 및 수락을 합니다.")
public class FriendController {
    private final FriendService friendService;
    @PostMapping("/add")
    public ResponseEntity<FriendResponseDto> addFriend(HttpServletRequest request, Long friendId) {
        String email = request.getRemoteUser();

        return ResponseEntity.ok(friendService.addFriend(email, friendId));

    }
    @PostMapping("/accept")
    public ResponseEntity<FriendResponseDto> acceptFriend(HttpServletRequest request, Long friendId) {
        String email = request.getRemoteUser();

        return ResponseEntity.ok(friendService.acceptFriend(email, friendId));
    }
}
