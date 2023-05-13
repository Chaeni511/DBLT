package com.dopamines.backend.wallet.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.review.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WalletServiceImpl {

    private final AccountRepository accountRepository;

    private final PlanRepository planRepository;

    private final ParticipantRepository participantRepository;

//    public Long settleMoney(String userEmail, Long planId) {
//
//        Account account = accountRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new IllegalArgumentException("해당 회원 정보가 없습니다."));
//
//        Plan plan = planRepository.findById(planId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 약속 정보가 없습니다."));
//
//        // 약속 참가자 들의 wllet 금액이 정산해야하는 금액 이상으로 가지고 있는지 체크
//        List<Participant> Participants = participantRepository.findByPlan(plan);
//    }
}
