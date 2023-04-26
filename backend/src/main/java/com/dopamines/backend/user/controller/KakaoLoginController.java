package com.dopamines.backend.user.controller;

import com.dopamines.backend.user.entity.User;
import com.dopamines.backend.user.service.UserService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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

    @PostMapping(value = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    String signup(@ModelAttribute String nickname, String email, String profile) {
        System.out.println(nickname + email + profile);
//            @RequestPart(value = "file", required = false) MultipartFile file,
//            @RequestPart(value = "nickname") String nickname,
//            @RequestPart(value = "email") String email,
//            @RequestPart(value = "phone") String phone

        User user = User.builder()
                .nickname(nickname)
                .email(email)
                .profile(profile)
                .build();
        Long userId = userService.signup(user);
        return makeToken(userId);
    }

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
//    ResponseEntity<Map<String, Object>> getAccessToken(@RequestParam String accessToken) {
    Map<String, Object> getAccessToken(@RequestParam String accessToken) {

//    	String access_Token = map.get("key");
        System.out.println("access token : " + accessToken);
        Map<String, Object> res = new HashMap<>();
        HttpStatus httpStatus = null;
        String email;
        try {
            email = getInfo(accessToken);
            System.out.println(email);

            if (email.equals("no_email")) {
                res.put("code", "noEmail");
//                res.put("")
                httpStatus = HttpStatus.OK;
            }
            System.out.println("getAccessToken에서 찍는 이메일이 없다.");

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
        System.out.println(res+"---------------------------------");
//        return new ResponseEntity<>(res, httpStatus);
        return res;


    }

    @PostMapping("/test")
    DefaultOAuth2User test(){
        return null;
    }
}

//{
//    "id":2762551263,
//    "connected_at":"2023-04-25T01:08:22Z",
//    "properties":{
//        "nickname":"이채은",
//        "profile_image":"http://k.kakaocdn.net/dn/CaTgY/btscyGINsid/Gd5TNMBYJkdD2ceLY4eWzk/img_640x640.jpg",
//        "thumbnail_image":"http://k.kakaocdn.net/dn/CaTgY/btscyGINsid/Gd5TNMBYJkdD2ceLY4eWzk/img_110x110.jpg"
//    },
//    "kakao_account":{
//        "profile_nickname_needs_agreement":false,
//        "profile_image_needs_agreement":false,
//        "profile":{
//            "nickname":"이채은",
//            "thumbnail_image_url":"http://k.kakaocdn.net/dn/CaTgY/btscyGINsid/Gd5TNMBYJkdD2ceLY4eWzk/img_110x110.jpg",
//            "profile_image_url":"http://k.kakaocdn.net/dn/CaTgY/btscyGINsid/Gd5TNMBYJkdD2ceLY4eWzk/img_640x640.jpg",
//            "is_default_image":false
//        },
//        "has_email":true,
//        "email_needs_agreement":false,
//        "is_email_valid":true,
//        "is_email_verified":true,
//        "email":"lce511@naver.com"
//    }
//}
