package com.dopamines.backend.webSocket.dto;

import com.dopamines.backend.plan.service.ParticipantService;
import com.dopamines.backend.plan.service.PlanService;
import com.dopamines.backend.webSocket.service.PositionService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Slf4j
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
//            positionService.updateIsArrived(message.getRoomId(), message.getSender(), false);
        }

        else if (message.getType().equals(MessageDto.MessageType.ARRIVE)) {
            // message 에서 getType 으로 가져온 내용이 ARRIVE 과 동일한 값이면
            // message 에는 도착하였다는 메시지를 전송
            message.setMessage(message.getSender() + "님이 도착했습니다.");
            // 도착시간, 약속시간과 차이, 도착여부 저장
//            positionService.updateIsArrived(message.getRoomId(), message.getSender(), true);
//            positionService.arrivedAllParticipant(this, message.getRoomId());
        }
        sendMessage(message, positionService);
    }


    public <T> void sendMessage(MessageDto message, PositionService positionService) {
        sessions.parallelStream().forEach(session -> positionService.sendMessage(session, message));
    }

}
