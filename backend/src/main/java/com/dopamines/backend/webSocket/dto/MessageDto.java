package com.dopamines.backend.webSocket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MessageDto {

    // 메시지 타입 : 입장, 움직임
    public enum MessageType{
        ENTER, MOVE
    }
    private MessageType type; // 메시지 타입
    private String roomId;// 약속 방 번호
    private String sender;// 움직임을 보낸 사람
    private String message;// 메세지
    private String time; // 움직임 발송 시간

}

// {
//"type":"ENTER",
//"roomId":"1",
//"sender":"김부경",
//"message":"위치 정보를 보냅니다.",
//"time":"12:11"
//}

// {
//"type":"MOVE",
//"roomId":"1",
//"sender":"김부경",
//"message":"위치 정보를 보냅니다.",
//"time":"12:11"
//}