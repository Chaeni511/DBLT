package com.dopamines.backend.plan.service;

import com.dopamines.backend.plan.dto.ParticipantDto;
import com.dopamines.backend.plan.dto.PlanDto;
import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.user.entity.User;
import com.dopamines.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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


    // 약속 삭제
    public void deletePlan(Plan plan) {
        planRepository.delete(plan);
    }


    // 진행 중인 약속 상세 정보
    public PlanDto getPlanDetail(Integer planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 약속 정보가 없습니다."));

        updatePlanStatus(plan);

        PlanDto planDto = new PlanDto();
        planDto.setPlanId(planId);
        planDto.setTitle(plan.getTitle());
        planDto.setDescription(plan.getDescription());
        planDto.setPlanDate(plan.getPlanDate());
        planDto.setPlanTime(plan.getPlanTime());
        planDto.setFind(plan.getFind());
        planDto.setLocation(plan.getLocation());
        planDto.setStatus(plan.getStatus());

        // 참가자 리스트 정보
        List<Participant> participants = participantRepository.findByPlan(plan);

        // 참가자 정보 dto 추가
        List<ParticipantDto> participantDtoList = new ArrayList<>();
        for (Participant participant : participants) {

            int designation = 0;
            Integer ArrivalTime = participant.getUser().getArrivalTime();

            if (ArrivalTime < 0) {
                // 일찍 오는 사람
                designation = 1;
            } else if (ArrivalTime > 0) {
                // 늦게 오는 사람
                designation = 2;
            }

            ParticipantDto participantDto = new ParticipantDto();
            participantDto.setUserId(participant.getUser().getUserId());
            participantDto.setNickname(participant.getUser().getNickname());
            participantDto.setProfile(participant.getUser().getProfile());
            participantDto.setIsHost(participant.getUser().equals(plan.getUser()));
            participantDto.setIsArrived(participant.getIsArrived());
            participantDto.setDesignation(designation);
            participantDtoList.add(participantDto);
        }
        planDto.setParticipantList(participantDtoList);

        return planDto;
    }


    // 모든 참가자가 도착한 경우 true 반환환
    public boolean isAllMemberArrived(Integer planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 약속 정보가 없습니다."));

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

    /////////////////////////////// 중복 사용 함수 ////////////////////////////////////////////
    // 약속 시간 유효성 검사
    public boolean isValidAppointmentTime(LocalDate planDate, LocalTime planTime) {
        LocalDateTime planDateTime = LocalDateTime.of(planDate, planTime);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        long diffMinutes = ChronoUnit.MINUTES.between(now, planDateTime);

        if (diffMinutes < 30) {
            return false;
        }
        return true;
    }


    // 약속 상태 변경 함수
    private void updatePlanStatus(Plan plan) {
        LocalDateTime planDateTime = LocalDateTime.of(plan.getPlanDate(), plan.getPlanTime());
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        long diffMinutes = ChronoUnit.MINUTES.between(now, planDateTime);

        if (diffMinutes > 30) {
            plan.setStatus(0); // 기본 상태
        } else if (diffMinutes > 0) {
            plan.setStatus(1); // 위치공유 (30분 전 ~ 약속시간)
        } else if (diffMinutes >= -60) {
            plan.setStatus(2); // 게임 활성화 (약속시간 ~ 1시간 후)
        } else {
            plan.setStatus(3); // 약속 종료 (1시간 이후)
        }

        planRepository.save(plan);
    }
}
