package com.dopamines.backend.plan.service;

import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.user.entity.User;
import com.dopamines.backend.user.repository.UserRepository;
import com.dopamines.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserRepository userRepository;


    public Plan savePlan(Plan plan) {
        planRepository.save(plan);
        return plan;
    }

    public ResponseEntity<Void> updatePlanAndParticipants(Integer userId, Integer planId, String title, String description, LocalDate planDate, LocalTime planTime, String location, Integer find, String newParticipantIdsStr) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Plan ID"));

        // 방장 확인
        if (!plan.getUser().getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 약속 정보를 업데이트
        plan.setTitle(title);
        plan.setDescription(description);
        plan.setPlanDate(planDate);
        plan.setPlanTime(planTime);
        plan.setLocation(location);
        plan.setFind(find);

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
                newParticipant.setArrived(false);
                participantsToAdd.add(newParticipant);
            }
        }

        // 제거해야 할 참가자 리스트 생성
        List<Participant> participantsToRemove = new ArrayList<>();
        for (Participant oldParticipant : oldParticipants) {
            // 새로운 참가자 목록에 없는 참가자이고 방장이 아닐 경우 삭제 대상에 추가
            if (!newParticipantIds.contains(oldParticipant.getUser().getUserId())
                    && !oldParticipant.getUser().getUserId().equals(plan.getUser().getUserId())) {
                participantsToRemove.add(oldParticipant);
            }

        }


        // 제거 대상 삭제, 추가 대상 추가
        participantRepository.deleteAll(participantsToRemove);
        participantRepository.saveAll(participantsToAdd);

        planRepository.save(plan);

        return ResponseEntity.ok().build();
    }


    // 모든 참가자가 도착한 경우 true 반환환
    public boolean isAllMemberArrived(Integer planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Plan ID"));

        List<Participant> participants = participantRepository.findByPlan(plan);
        // 현재 참여중인 멤버들의 도착 여부를 확인합니다.
        // 모두 도착했으면 true, 한사람이라도 도착하지 않았다면 false를 반환합니다.
        for (Participant participant : participants) {
            if (!participant.isArrived()) {
                return false;
            }
        }
        return true;
    }

}
