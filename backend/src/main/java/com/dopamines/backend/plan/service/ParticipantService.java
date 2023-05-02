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
import java.util.*;
import java.util.stream.Collectors;

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
        log.info(user.getEmail() + " 님이 참가되었습니다");
    }


    // 참가자 수정
    public void updateParticipant(Plan plan, String newParticipantIdsStr) {

        // 이전 참가자 목록 조회
        List<Participant> oldParticipants  = participantRepository.findByPlan(plan);

        // 이전 참가자들의 ID 집합
        Set<Integer> oldParticipantIds = oldParticipants .stream()
                .map(participant -> participant.getUser().getUserId())
                .collect(Collectors.toSet());

        // 새로운 참가자 ID 목록 파싱
        List<Integer> newParticipantIds = new ArrayList<>();
        if (newParticipantIdsStr != null && !newParticipantIdsStr.isEmpty()) {
            newParticipantIds = Arrays.stream(newParticipantIdsStr.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }

        // 새로 추가해야 할 참가자 리스트 생성
        List<Participant> participantsToAdd = new ArrayList<>();
        for (Integer newParticipantId : newParticipantIds) {
            // 기존 참가자가 아니라면 추가
            if (!oldParticipantIds.contains(newParticipantId)) {
                // 새로운 참가자 객체 생성
                User newUser = userRepository.findById(newParticipantId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid User ID: " + newParticipantId));
                Participant newParticipant = new Participant();
                newParticipant.setPlan(plan);
                newParticipant.setUser(newUser);
                newParticipant.setIsArrived(false);
                participantsToAdd.add(newParticipant);
                log.info("참가자 추가: " + newUser.getEmail());
            }
        }

        // 제거해야 할 참가자 리스트 생성
        List<Participant> participantsToRemove = new ArrayList<>();
        for (Participant oldParticipant : oldParticipants) {
            // 새로운 참가자 목록에 없는 참가자이고 방장이 아닐 경우 삭제 대상에 추가
            if (!newParticipantIds.contains(oldParticipant.getUser().getUserId())
                    && !oldParticipant.getUser().getUserId().equals(plan.getUser().getUserId())) {
                participantsToRemove.add(oldParticipant);
                log.info("참가자 삭제: " + oldParticipant.getUser().getEmail());
            }

        }

        // 제거 대상 삭제, 추가 대상 추가
        participantRepository.deleteAll(participantsToRemove);
        participantRepository.saveAll(participantsToAdd);
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
