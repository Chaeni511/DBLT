package com.dopamines.backend.webSocket.dto;

import com.dopamines.backend.webSocket.service.PositionService;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PlanRoomDto {

    // 위치 방 아이디
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();


    @Builder
    public PlanRoomDto(String roomId) {
        this.roomId = roomId;
    }


    public void handleAction(WebSocketSession session, MessageDto message, PositionService positionService) {
        // message 에 담긴 타입을 확인
        // message 에서 getType 으로 가져온 내용이 ENTER 과 동일한 값이면
        if (message.getType().equals(MessageDto.MessageType.ENTER)) {
            // 넘어온 session을 sessions에 담고
            sessions.add(session);
            // message 에는 입장하였다는 메시지를 전송
            message.setMessage(message.getSender() + "님이 입장했습니다.");
        }
//        } else if (message.getType().equals(MessageDto.MessageType.VIEW)) {
//            // message 에서 getType 으로 가져온 내용이 VIEW 과 동일한 값이면
//            // message 에는 위치 메시지를 전송
//            message.setMessage(message.getMessage());
//        }
        sendMessage(message, positionService);
    }


    public <T> void sendMessage(MessageDto message, PositionService positionService) {
        sessions.parallelStream().forEach(session -> positionService.sendMessage(session, message));
    }

}
