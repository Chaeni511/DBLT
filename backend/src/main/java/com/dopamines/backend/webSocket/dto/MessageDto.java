package com.dopamines.backend.webSocket.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MessageDto {

    // 메시지 타입 : 입장, 움직임
    public enum MessageType{
        ENTER, MOVE, ARRIVE
    }
    private MessageType type; // 메시지 타입
    private String roomId; // 약속 방 번호
    private String sender; // 움직임을 보낸 사람 accountId
    private String message; // 메세지
    private String latitude; // 위도
    private String longitude; // 경도

}

// {
//"type":"ENTER",
//"roomId":"1",
//"sender":"1",
//"message":"위치 정보를 보냅니다.",
//"latitude":"35.878",
//"longitude":"128.6287"
//}

// {
//"type":"MOVE",
//"roomId":"1",
//"sender":"1",
//"message":"위치 정보를 보냅니다.",
//"latitude":"35.878",
//"longitude":"128.6287",
//}