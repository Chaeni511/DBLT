package com.dopamines.backend.friend.controller;

import com.dopamines.backend.friend.dto.FriendResponseDto;
import com.dopamines.backend.friend.entity.Friend;
import com.dopamines.backend.friend.service.FriendService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "friend", description = "친구 신청 및 수락을 합니다.")
public class FriendController {
    private final FriendService friendService;

    @GetMapping("/get")
    public ResponseEntity<List<Friend>> getFriendList(HttpServletRequest request){
        String email = request.getRemoteUser();

        return ResponseEntity.ok(friendService.getFriendList(email));
    }

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

    @DeleteMapping("/deny")
    public ResponseEntity<FriendResponseDto> denyFriend(HttpServletRequest request, Long friendId) {
        String email = request.getRemoteUser();

        return ResponseEntity.ok(friendService.denyFriend(email, friendId));
    }

    @DeleteMapping("/delete") //친구삭제
    public FriendResponseDto deleteFriend (HttpServletRequest request,  Long friendId) {
        String email = request.getRemoteUser();

        return friendService.deleteFriend(email, friendId);
    }

}
