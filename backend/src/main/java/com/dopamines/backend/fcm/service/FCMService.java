package com.dopamines.backend.fcm.service;

import com.dopamines.backend.fcm.dto.GroupTokenListDto;

import java.io.IOException;

public interface FCMService {

    // 토큰 저장
    void registerToken(String userEmail, String deviceToken);

    // 토큰 수정
    void updateToken(String userEmail, String deviceToken);

    // 토큰 삭제
    void deleteToken(String userEmail);

    // 참가자 리스트 가져오기
    GroupTokenListDto getGroupToken(Long planId);

    void sendMessageTo(String targetToken, String title, String body) throws IOException;


}
