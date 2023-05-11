package com.dopamines.backend.friend.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.friend.entity.WaitingFriend;
import com.dopamines.backend.friend.repository.FriendRepository;
import com.dopamines.backend.friend.repository.WaitingFriendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService{
    private final AccountRepository accountRepository;
    private final FriendRepository friendRepository;
    private final WaitingFriendRepository waitingFriendRepository;
    private FriendService friendService;
    @Override
    public void addFriend(String email, Long friendId){
        Optional<Account> myAccount = accountRepository.findByEmail(email);
        Optional<Account> friendAccount = accountRepository.findById(friendId);

        // 나한테 친신 했을 때
        if(myAccount.get().getAccountId()==friendAccount.get().getAccountId()){
            throw new RuntimeException(("나는 세상에서 제일 소중한 친구입니다:) "));
        }

        // 이미 친구인지 확인
//        for(Friend myFriend : myFriends)
//        {
//            if(myFriend.getFriendId()==friendId)
//                throw new RuntimeException("이미 친구입니다.");
//        }
        log.info("addFriend에서 찍는 myAccount: " + myAccount.get().getEmail());
        log.info("addFriend에서 찍는 friendAccount: " + friendAccount.get().getEmail());

        WaitingFriend waitingFriend = WaitingFriend.toBuild(myAccount.get(), friendAccount.get());
        log.info("addFriend에서 찍는 waitingFriend: " + waitingFriend.getFriendEmail());
        log.info("addFriend에서 찍는 waitingFriend: " + waitingFriend.getAccount().getEmail());
        waitingFriendRepository.save(waitingFriend);
    }

    @DeleteMapping("/delete") //친구삭제
    public Boolean deleteFriend(Long friendId)
    {
        return friendService.deleteFriend(friendId);
    }
}
