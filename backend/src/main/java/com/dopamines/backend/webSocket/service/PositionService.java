package com.dopamines.backend.webSocket.service;

import com.dopamines.backend.plan.service.PlanService;
import com.dopamines.backend.webSocket.dto.PlanRoomDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PositionService {

    @Autowired
    private PlanService planService;

    private final ObjectMapper objectMapper;
    private Map<String, PlanRoomDto> planRooms;


    @PostConstruct
    private void init() {
        planRooms = new LinkedHashMap<>();
    }


    public List<PlanRoomDto> findAllRoom() {
        return new ArrayList<>(planRooms.values());
    }


    public PlanRoomDto findRoomById(String roomId) {
        return planRooms.get(roomId);
    }


    public PlanRoomDto createRoom(String planId) {
        PlanRoomDto planRoom = PlanRoomDto.builder()
                .roomId(planId)
                .build();
        planRooms.put(planId, planRoom);

        return planRoom;
    }

    // 메시지 보내기
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    // 모든 참가자가 도착했는지 확인하고 모두 도착했으면 세션을 종료하고 방을 제거
    public void arrivedAllParticipant(PlanRoomDto room, String planId) {

        if (planService.isAllMemberArrived(Integer.parseInt(planId))) {
            // 모든 참가자가 도착한 경우
            log.info("All members arrived for planId : {}", planId);

            // 모든 세션을 종료합니다.
            for (WebSocketSession session : room.getSessions()) {
                try {
                    session.close();
                } catch (IOException e) {
                    log.error("Error while closing session", e);
                }
            }
            // 방을 제거합니다.
            planRooms.remove(planId);
        }
    }
}
