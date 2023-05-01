package com.dopamines.backend.plan.service;

import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.user.entity.User;
import com.dopamines.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserService userService;


    public Plan savePlan(Plan plan) {
        planRepository.save(plan);
        return plan;
    }

    public void updatePlan(Integer userId, Integer planId, String title, String description, LocalDate planDate, LocalTime planTime, String location, Integer find, String participantIdsStr) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Plan ID"));

        if (!plan.getUser().getUserId().equals(userId)) {
            throw new NotRoomOwnerException("You are not the owner of this room.");
        }

        plan.setTitle(title);
        plan.setDescription(description);
        plan.setPlanDate(planDate);
        plan.setPlanTime(planTime);
        plan.setLocation(location);
        plan.setFind(find);
        planRepository.save(plan);






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
