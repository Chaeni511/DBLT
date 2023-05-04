package com.dopamines.backend.plan.service;

import com.dopamines.backend.plan.dto.PlanDto;
import com.dopamines.backend.plan.dto.PlanListDto;
import com.dopamines.backend.plan.entity.Plan;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PlanService {

    // 약속 생성
    Long createPlan(String userEmail, String title, LocalDate planDate, LocalTime planTime, String location, Integer find, String participantIdsStr);

    // 약속 수정
    void updatePlanAndParticipant(Plan plan, String title, LocalDate planDate, LocalTime planTime, String location, Integer find, String newParticipantIdsStr);

    // 약속 삭제
    void deletePlan(Plan plan);

    // 진행 중인 약속 상세 정보
    PlanDto getPlanDetail(Long planId);

    // 해당 날짜의 약속 리스트
    List<PlanListDto> getPlanList(String userEmail, LocalDate planDate);

    // 모든 참가자가 도착한 경우 true 반환환
    boolean isAllMemberArrived(Long planId);



    //////////////// 중복 ///////////////////

    // 약속 유효성 검사
    Plan getPlanById(Long planId);

    // 약속 시간 유효성 검사
    // 시간
    long getTimeHoursDifference(LocalDate planDate, LocalTime planTime);
    // 분
    long getTimeMinutesDifference(LocalDate planDate, LocalTime planTime);


}
