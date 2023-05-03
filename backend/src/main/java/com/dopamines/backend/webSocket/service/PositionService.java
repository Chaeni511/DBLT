package com.dopamines.backend.webSocket.service;

import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.plan.service.PlanService;
import com.dopamines.backend.user.entity.User;
import com.dopamines.backend.user.repository.UserRepository;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PositionService {

    @Autowired
    private PlanService planService;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;

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


    // 도착하면 도착상태, 도착시간, 지각시간 업데이트
    // 입장하면 도착상태 false로 변환
    @Transactional
    public void updateIsArrived(String planId, String nickname, boolean isArrived) {
        Plan plan = planRepository.findById(Integer.parseInt(planId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid Plan ID"));
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Participant participant = participantRepository.findByPlanAndUser(plan, user)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found"));

        // 도착상태 변화
        participant.setIsArrived(isArrived);

        // 도착했으면 시간저장
        if (isArrived) {
            LocalTime arrivalTime = LocalTime.now(ZoneId.of("Asia/Seoul"));
            participant.setArrivalTime(arrivalTime);
            // 지각 시간 계산
            Duration duration = Duration.between(arrivalTime, plan.getPlanTime());
            long minutesBetween = duration.toMinutes();
            participant.setLateTime(minutesBetween);
        }
        // 변경사항 저장
        participantRepository.save(participant);
    }
}
