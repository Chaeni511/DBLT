package com.dopamines.backend.plan.service;

import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.user.entity.User;
import com.dopamines.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;


    // 참가자 생성
    public void createParticipant(User user, Plan plan) {
        Participant participant = Participant.builder()
                .user(user)
                .plan(plan)
                .isArrived(false)
                .build();
        participantRepository.save(participant);
        log.info(user.getEmail(), "님이 참가되었습니다");
    }


    // 도착하면 도착상태, 도착시간, 지각시간 업데이트
    // 입장하면 도착상태 false로 변환
    @Transactional
    public void updateIsArrived(String planId, String nickname, Boolean isArrived) {
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
            participant.setArrivalDt(arrivalTime);
            // 지각 시간 계산
            Duration duration = Duration.between(arrivalTime, plan.getPlanTime());
            long minutesBetween = duration.toMinutes();
            participant.setLateTime(minutesBetween);
        }
        // 변경사항 저장
        participantRepository.save(participant);
    }
}
