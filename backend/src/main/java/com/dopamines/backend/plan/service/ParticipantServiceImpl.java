package com.dopamines.backend.plan.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private AccountRepository accountRepository;


    // 참가자 생성
    @Override
    public void createParticipant(Account account, Plan plan, Boolean host) {
        Participant participant = Participant.builder()
                .account(account)
                .plan(plan)
                .isArrived(false)
                .isHost(host)
                .build();
        participantRepository.save(participant);
        log.info(account.getEmail() + " 님이 참가되었습니다");
    }


    // 참가자 수정
    @Override
    public void updateParticipant(Plan plan, String newParticipantIdsStr) {

        // 이전 참가자 목록 조회
        List<Participant> oldParticipants  = participantRepository.findByPlan(plan);

        // 이전 참가자들의 ID 집합
        Set<Long> oldParticipantIds = oldParticipants.stream()
                .map(participant -> participant.getAccount().getAccountId())
                .collect(Collectors.toSet());

        // 새로운 참가자 ID 목록 파싱
        List<Long> newParticipantIds = new ArrayList<>();
        if (newParticipantIdsStr != null && !newParticipantIdsStr.isEmpty()) {
            newParticipantIds = Arrays.stream(newParticipantIdsStr.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }

        // 새로 추가해야 할 참가자 리스트 생성
        List<Participant> participantsToAdd = new ArrayList<>();
        for (Long newParticipantId : newParticipantIds) {
            // 기존 참가자가 아니라면 추가
            if (!oldParticipantIds.contains(newParticipantId)) {
                // 새로운 참가자 객체 생성
                Account newUser = accountRepository.findById(newParticipantId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid User ID: " + newParticipantId));
                Participant newParticipant = new Participant();
                newParticipant.setPlan(plan);
                newParticipant.setAccount(newUser);
                newParticipant.setIsArrived(false);
                newParticipant.setIsHost(false);
                participantsToAdd.add(newParticipant);
                log.info("참가자 추가: " + newUser.getEmail());
            }
        }

        // 제거해야 할 참가자 리스트 생성
        List<Participant> participantsToRemove = new ArrayList<>();
        for (Participant oldParticipant : oldParticipants) {
            // 새로운 참가자 목록에 없는 참가자이고 방장이 아닐 경우 삭제 대상에 추가
            if (!newParticipantIds.contains(oldParticipant.getAccount().getAccountId())
                    && !oldParticipant.getIsHost()) {
                participantsToRemove.add(oldParticipant);
                log.info("참가자 삭제: " + oldParticipant.getAccount().getEmail());
            }

        }

        // 제거 대상 삭제, 추가 대상 추가
        participantRepository.deleteAll(participantsToRemove);
        participantRepository.saveAll(participantsToAdd);
    }


    //////////////////////////// 중복 ////////////////////////////

    // 방장 인지 확인
    @Override
    public boolean findIsHostByPlanAndUser(Plan plan, Account account) {
        Participant participant = participantRepository.findByPlanAndAccount(plan, account)
                .orElseThrow(() -> new IllegalArgumentException("참가자가 아닙니다."));

        return participant.getIsHost();
    }

}
