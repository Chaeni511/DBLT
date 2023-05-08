package com.dopamines.backend.plan.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.plan.entity.Plan;

public interface ParticipantService {

    // 참가자 생성
    void createParticipant(Account account, Plan plan, Boolean host);

    // 참가자 수정
    void updateParticipant(Plan plan, String newParticipantIdsStr);

    // 방장 인지 확인
    Boolean findIsHostByPlanAndUser(Plan plan, Account account);

}
