package com.dopamines.backend.plan.service;

import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.user.entity.User;
import com.dopamines.backend.user.repository.UserRepository;
import com.dopamines.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {

    @Autowired
    private UserService userService;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantService participantService;


    // 약속 생성
    public Integer createPlan(Integer userId, String title, String description, LocalDate planDate, LocalTime planTime, String location, Integer find, String participantIdsStr) {

        User user = userService.findByUserId(userId);

        Plan plan = planRepository.save(
                Plan.builder()
                        .user(user)
                        .title(title)
                        .description(description)
                        .planDate(planDate)
                        .planTime(planTime)
                        .location(location)
                        .find(find)
                        .status(0)
                        .build()
        );

        // 방장 참가자로 추가
        participantService.createParticipant(user, plan);

        // 참가자 추가
        if (participantIdsStr != null && !participantIdsStr.isEmpty()) {
            String[] participantIds = participantIdsStr.split(",");
            for (String participantId : participantIds) {
                User participant = userService.findByUserId(Integer.valueOf(participantId));
                participantService.createParticipant(participant, plan);
            }
        }

        return plan.getPlanId();
    }


    // 약속 수정
    public void updatePlanAndParticipant(Plan plan, String title, String description, LocalDate planDate, LocalTime planTime, String location, Integer find, String newParticipantIdsStr) {

        // 참가자 수정
        participantService.updateParticipant(plan, newParticipantIdsStr);

        // 약속 정보를 업데이트
        plan.setTitle(title);
        plan.setDescription(description);
        plan.setPlanDate(planDate);
        plan.setPlanTime(planTime);
        plan.setLocation(location);
        plan.setFind(find);

        planRepository.save(plan);

    }


    public void deletePlan(Plan plan) {
        planRepository.delete(plan);
    }


    // 모든 참가자가 도착한 경우 true 반환환
    public boolean isAllMemberArrived(Integer planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Plan ID"));

        List<Participant> participants = participantRepository.findByPlan(plan);
        // 현재 참여중인 멤버들의 도착 여부를 확인합니다.
        // 모두 도착했으면 true, 한사람이라도 도착하지 않았다면 false를 반환합니다.
        for (Participant participant : participants) {
            if (!participant.getIsArrived()) {
                return false;
            }
        }
        return true;
    }

}
