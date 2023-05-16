package com.dopamines.backend.fcm.service;

import java.io.IOException;

public interface FCMService {

    void registerToken(String userEmail, String deviceToken);

    void updateToken(String userEmail, String deviceToken);

    void sendMessageTo(String targetToken, String title, String body) throws IOException;


}
