package com.dopamines.backend.webSocket.handler;

import com.dopamines.backend.webSocket.dto.MessageDto;
import com.dopamines.backend.webSocket.dto.PlanRoomDto;
import com.dopamines.backend.webSocket.service.PositionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PositionHandler extends TextWebSocketHandler {
    // 텍스트 기반의 채팅을 구현: 'TextWebSocketHandler'를 상속받아서 작성
    // Client로부터 받은 메세지를 Log출력하고 클라이언트에게 환영하는 메세지를 보낸다.

    private final ObjectMapper objectMapper;
    private final PositionService positionService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 웹소켓 클라이언트가 텍스트 메시지를 전송할 때 호출
        // TextMessage()는 그 텍스트 메시지의 정보를 담고있다.

        log.info("# PositionHandler, handleMessage");

        // 전송되는 데이터
        String payload = message.getPayload();
        log.info("payload : {}", payload);

        log.info("-------------------------------------session 차례");

        MessageDto positionMessage = objectMapper.readValue(payload, MessageDto.class);
        log.info("session : {}", positionMessage.toString());

        log.info("-------------------------------------planRoom 차례");

        PlanRoomDto planRoom = positionService.findRoomById(positionMessage.getRoomId());
        log.info("planRoom : {}", planRoom.toString());

        planRoom.handleAction(session, positionMessage, positionService);



        /* =================== 테스트 코드 =================== */
//        Map<String, String> testRes = new HashMap<>();
//        testRes.put("testRes", "ㅎㅎㅎ");
//        for(WebSocketSession s : sessionList) {
//            s.sendMessage(new TextMessage("'testRes':'ggg'"));
//        }
        /* =================== 테스트 코드 끝 ==================== */
    }


    /** Client가 접속 시 호출되는 메서드*/
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(session + " 클라이언트 접속");
    }


    /** client가 접속 시 호출되는 메서드*/
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + " 클라이언트 접속 해제");
    }
}