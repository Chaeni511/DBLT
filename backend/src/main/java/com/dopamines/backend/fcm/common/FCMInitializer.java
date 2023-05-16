package com.dopamines.backend.fcm.common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;


@Slf4j
@Component
// 어플리케이션이 시작될 때 비공개 키 파일의 인증정보를 이용해 FirebaseApp을 초기화하도록 FCMInitializer 구현
public class FCMInitializer {

//    @PostConstruct
//    public void initializer() throws IOException {
//
//        FileInputStream serviceAccount =
//                new FileInputStream("/firebase/d209-dopamines-firebase-adminsdk-pz1bc-a666dc4e60.json");
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        FirebaseApp.initializeApp(options);
//    }

}
