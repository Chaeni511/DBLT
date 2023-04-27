package com.dopamines.backend.webSocket.service;

import com.dopamines.backend.webSocket.dto.PlanRoomDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionService {

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


    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
