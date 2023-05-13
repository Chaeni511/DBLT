package com.dopamines.backend.wallet.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.wallet.dto.SettlementDto;
import com.dopamines.backend.wallet.dto.SettlementResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WalletServiceImpl {

    private final AccountRepository accountRepository;

    private final PlanRepository planRepository;

    private final ParticipantRepository participantRepository;


    // 정산 가능한지 확인 (각 참가자의 지갑 금액이 정산해야하는 금액보다 많은 지 확인)
    private boolean checkParticipantWallets(List<Participant> participants) {
        for (Participant participant : participants) {
            // 지불해야 하는 사람중에
            if (participant.getTransactionMoney() < 0) {
                if (participant.getAccount().getTotalWallet() < Math.abs(participant.getTransactionMoney())) {
                    // 참가자가 정산금액 보다 적게 가지고 있으면
                    return false;
                }
            }
        }
        // 모든 참가자가 충분하게 가지고 있음
        return true;
    }

    // 정산 가능한지 확인 (각 참가자의 지갑 금액이 정산해야하는 금액보다 많은 지 확인) 적은사람이있으면 dto로 보내줌
    private List<SettlementDto> isSettle(List<Participant> participants) {
        List<SettlementDto> participantsWithInsufficientWallet = new ArrayList<>();

        for (Participant participant : participants) {
            if (participant.getTransactionMoney() < 0) {
                if (participant.getAccount().getTotalWallet() < Math.abs(participant.getTransactionMoney())) {
                    SettlementDto poorParticipantdto = new SettlementDto();
                    poorParticipantdto.setAccountId(participant.getAccount().getAccountId());
                    poorParticipantdto.setNickName(participant.getAccount().getNickname());
                    poorParticipantdto.setPaymentAmount(participant.getTransactionMoney());
                    participantsWithInsufficientWallet.add(poorParticipantdto);
                }
            }
        }
        return participantsWithInsufficientWallet;
    }


    public SettlementResultDto settleMoney(String userEmail, Long planId) {

        Account account = accountRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 정보가 없습니다."));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("해당 약속 정보가 없습니다."));


        // 약속 참가자들의 wallet 금액이 정산해야하는 금액 이상으로 가지고 있는지 체크
        List<Participant> participants = participantRepository.findByPlan(plan);
        List<SettlementDto> poorParticipants = isSettle(participants);

        if (poorParticipants.isEmpty()) {
            // 정산 성공
            log.info("정산이 완료되었습니다.");
            return new SettlementResultDto(true, poorParticipants);
        } else {
            // 정산 실패
            log.info("정산 실패: 일부 참가자의 지갑 금액이 부족합니다.");
            return new SettlementResultDto(false, poorParticipants);
        }
    }
}
