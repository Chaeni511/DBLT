package com.dopamines.backend.plan.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.service.UserService;
import com.dopamines.backend.plan.dto.ParticipantDto;
import com.dopamines.backend.plan.dto.PlanDto;
import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
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
public class PlanServiceImpl implements PlanService {

    @Autowired
    private UserService userService;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantServiceImpl participantService;


    // 약속 생성
    @Override
    public Long createPlan(Long accountId, String title, LocalDate planDate, LocalTime planTime, String location, Integer find, String participantIdsStr) {

        Account account = userService.findByAccountId(accountId);

        Plan plan = planRepository.save(
                Plan.builder()
                        .title(title)
                        .planDate(planDate)
                        .planTime(planTime)
                        .location(location)
                        .find(find)
                        .status(0)
                        .build()
        );

        // 방장 참가자로 추가
        participantService.createParticipant(account, plan, true);

        // 참가자 추가
        if (participantIdsStr != null && !participantIdsStr.isEmpty()) {
            String[] participantIds = participantIdsStr.split(",");
            for (String participantId : participantIds) {
                if (Long.valueOf(participantId).equals(accountId)) {
                    continue;
                }
                Account participant = userService.findByAccountId(Long.valueOf(participantId));
                participantService.createParticipant(participant, plan, false);
            }
        }

        return plan.getPlanId();
    }


    // 약속 수정
    @Override
    public void updatePlanAndParticipant(Plan plan, String title, LocalDate planDate, LocalTime planTime, String location, Integer find, String newParticipantIdsStr) {

        // 참가자 수정
        participantService.updateParticipant(plan, newParticipantIdsStr);

        // 약속 정보를 업데이트
        plan.setTitle(title);
        plan.setPlanDate(planDate);
        plan.setPlanTime(planTime);
        plan.setLocation(location);
        plan.setFind(find);

        planRepository.save(plan);
    }


    // 약속 삭제
    @Override
    public void deletePlan(Plan plan) {
        planRepository.delete(plan);
    }


    // 진행 중인 약속 상세 정보
    @Override
    public PlanDto getPlanDetail(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("해당 약속의 약속 정보가 없습니다."));

        updatePlanStatus(plan);

        PlanDto planDto = new PlanDto();
        planDto.setPlanId(planId);
        planDto.setTitle(plan.getTitle());
        planDto.setPlanDate(plan.getPlanDate());
        planDto.setPlanTime(plan.getPlanTime());
        planDto.setFind(plan.getFind());
        planDto.setLocation(plan.getLocation());
        planDto.setStatus(plan.getStatus());

        // D-day 계산
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        long diffDay = ChronoUnit.DAYS.between(today, plan.getPlanDate());

        // D-day
        planDto.setDiffDay(diffDay);

        // 참가자 리스트 정보
        List<Participant> participants = participantRepository.findByPlan(plan);

        // 참가자 수
        planDto.setParticipantCount(participants.size());

        // 참가자 정보 dto 추가
        List<ParticipantDto> participantDtoList = new ArrayList<>();
        for (Participant participant : participants) {

            int designation = 0;
            int ArrivalTime = participant.getAccount().getAccumulatedTime();

            if (ArrivalTime < 0) {
                // 일찍 오는 사람
                designation = 1;
            } else if (ArrivalTime > 0) {
                // 늦게 오는 사람
                designation = 2;
            }

            ParticipantDto participantDto = new ParticipantDto();
            participantDto.setAccountId(participant.getAccount().getAccountId());
            participantDto.setNickname(participant.getAccount().getNickname());
            participantDto.setProfile(participant.getAccount().getProfile());
            participantDto.setIsHost(participant.getIsHost());
            participantDto.setIsArrived(participant.getIsArrived());
            participantDto.setDesignation(designation);
            participantDtoList.add(participantDto);
        }
        planDto.setParticipantList(participantDtoList);

        return planDto;
    }


    // 모든 참가자가 도착한 경우 true 반환환
    @Override
    public boolean isAllMemberArrived(Long planId) {
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

    // 약속 유효성 검사
    @Override
    public Plan getPlanById(Long planId) {
        return planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 planId 입니다."));
    }

    // 약속 시간 유효성 검사
    @Override
    public boolean isValidAppointmentTime(LocalDate planDate, LocalTime planTime) {
        LocalDateTime planDateTime = LocalDateTime.of(planDate, planTime);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        long diffMinutes = ChronoUnit.MINUTES.between(now, planDateTime);

        return diffMinutes > 0;
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
