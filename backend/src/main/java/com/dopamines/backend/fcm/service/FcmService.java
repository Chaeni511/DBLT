package com.dopamines.backend.fcm.service;

import com.dopamines.backend.fcm.dto.FcmMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class FcmService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/d209-dopamines/messages:send";

    private final ObjectMapper objectMapper;

    // targetToken에 해당하는 device로 FCM 푸시알림을 전송 요청
    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);

        // OkHttp3 를 이용해, Http Post Request를 생성
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                // header에 AccessToken을 추가
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();

        log.info("성공하면 각 전송 메서드가 메시지 ID를 반환");
        log.info(response.body().string());
    }

    // FcmMessage를 만들고, 이를 ObjectMapper을 이용해 String으로 변환하여 반환
    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .build()
                )
                .validateOnly(false)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    // FCM을 이용할수 있는 권한이 부여된 Oauth2의 AccessToken을 받음
    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "/firebase/d209-dopamines-firebase-adminsdk-pz1bc-a666dc4e60.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        //  AccessToken은 RestAPI를 이용해 FCM에 Push 요청을 보낼때, Header에 설정하여, 인증을 위해 사용
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
