package com.dopamines.backend.plan.service;

import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.user.entity.User;
import com.dopamines.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    UserRepository userRepository;


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
        participant.setArrived(isArrived);

        // 도착했으면 시간저장
        if (isArrived) {
            LocalDateTime arrivalTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            participant.setArrivalDt(arrivalTime);
            // 지각 시간 계산
            Duration duration = Duration.between(arrivalTime, plan.getPlanDt());
            long minutesBetween = duration.toMinutes();
            participant.setLateTime(minutesBetween);
        }
        // 변경사항 저장
        participantRepository.save(participant);
    }
}
