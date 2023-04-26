package com.dopamines.backend.user.controller;

import com.dopamines.backend.user.entity.User;
import com.dopamines.backend.user.service.UserService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/user")
public class KakaoLoginController {

    @Value("${KAKAO_KEY}")
    String secretKey;

    final private UserService userService;


    String getInfo(String token) throws IOException {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        URL url;
        try {
            url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            System.out.println("id : " + id);
            System.out.println("email : " + email);
            return email;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return "noEmail";
        }
    }

    String makeToken(Long user_id) {
        System.out.println("makeToken");
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000);
        System.out.println("여기도 되나요??");
        System.out.println("secret key" + secretKey);
        return Jwts.builder()
                .setSubject("access_key") // 열쇠? 키?
                .setIssuedAt(new Date()) // 발행일
                .setExpiration(expiryDate) // 만료일
                //만약 claim을 넣고 싶다면
                .claim("user_id", user_id) // 넣을 payload 키
//                .signWith(SignatureAlgorithm.HS512, secretKey) // 암호화 방식
                .compact(); // 묶어
    }

    @GetMapping("/login")
    ResponseEntity<Map<String, Object>> getAccessToken(@RequestParam String accessToken) {
//    	String access_Token = map.get("key");
        System.out.println("access token : " + accessToken);
        Map<String, Object> res = new HashMap<>();
        HttpStatus httpStatus = null;
        String email;
        try {
            email = getInfo(accessToken);
            System.out.println(email);

            if (email.equals("noEmail")) {
                res.put("code", "noEmail");
                httpStatus = HttpStatus.OK;
            }
            System.out.println("getAccessTokend에서 찍는 이메일이 없다.");

            //이메일을 데이터베이스에서 뒤져요
            User user = userService.findUserByEmail(email);
            //이메일이 있으면 이미 가입한 유저에요 JWT토큰을 만들어 홈으로 userDto와 함께 가요
            //이메일이 없으면 가입한 적이 없는 유저에요
            if (user == null) {
                res.put("code", "noEmail");
                res.put("result", email);
                httpStatus = HttpStatus.OK;
            } else {
                res.put("code", "member");
                res.put("result", makeToken(user.getUserId()));
                httpStatus = HttpStatus.OK;
            }
        } catch (Exception e) {
            res.put("code", e.getMessage());
            httpStatus = HttpStatus.EXPECTATION_FAILED;
        }
        System.out.println(res);
        return new ResponseEntity<>(res, httpStatus);
    }

//    @PostMapping("/test")

}

