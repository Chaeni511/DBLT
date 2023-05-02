package com.dopamines.backend.account.config;

import com.dopamines.backend.account.dto.KakaoUserInfoResponseDto;

import com.dopamines.backend.account.dto.TokenResponseDto;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.spring.web.json.Json;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class KakaoLoginConfig {
    private final WebClient webClient;
    private static final String USER_INFO_URI = "http://localhost:8081/account/oauth";

    public Mono<TokenResponseDto> login(String email, Long kakaoId) {
        String uri = USER_INFO_URI;

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("email", email);
        bodyMap.put("kakaoId", kakaoId.toString());

        return webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Bearer " + accessToken)
                .body(BodyInserters.fromValue(bodyMap))
                .retrieve()
                .bodyToMono(TokenResponseDto.class);
    }
//    public Json login(String email, Long kakaoId) {
//        String uri = USER_INFO_URI;
//
//        Map<String, String> bodyMap = new HashMap<>();
//        bodyMap.put("email", email);
//        bodyMap.put("kakaoId", kakaoId.toString());
//
//        Flux<Json> response = webClient.post()
//                .uri(uri)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(bodyMap))
//                .retrieve()
//                .bodyToFlux(Json.class);
////                .header("Authorization", "Bearer " + token)
//
//        return response.blockFirst();

//        return response.blockFirst();

}