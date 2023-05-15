package com.dopamines.backend.game.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import com.dopamines.backend.game.dto.GameResponseDto;
import com.dopamines.backend.game.dto.MyCharacterDto;
import com.dopamines.backend.game.repository.ItemRepository;
import com.dopamines.backend.game.repository.MyCharacterRepository;
import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService{

    private final ItemRepository itemRepository;
    private final AccountRepository accountRepository;
    private final MyCharacterRepository myCharacterRepository;
    private final ParticipantRepository participantRepository;
    private final PlanRepository planRepository;
    private final MyCharacterService myCharacterService;
    @Override
    public GameResponseDto enterGame(String email, Long planId) {
        log.info("planId: " + planId);
        MyCharacterDto myCharacterDto = myCharacterService.getMyCharacter(email);

        Optional<Plan> plan = planRepository.findById(planId);
        Optional<Account> account = accountRepository.findByEmail(email);
        log.info("plan isPresent : " + plan.isPresent());
        log.info("account isPresent : " + account.isPresent());
        Optional<Participant> participant;
        if (plan.isEmpty() || account.isEmpty()){
            log.info("plan.isEmpty()");
            participant = null;
            throw new RuntimeException("plan이 없습니다.");
        } else {
            participant = participantRepository.findByPlanAndAccount(plan.get(), account.get());
            log.info("participant isPresent : " + participant.isPresent());
        }

        int transactionMoney = participant.get().getTransactionMoney();

        return new GameResponseDto(account.get().getNickname(), myCharacterDto, transactionMoney);
    }



    @Override
    public void exitGame(String email, Long planId, int money){
        Optional<Plan> plan = planRepository.findById(planId);
        Optional<Account> account = accountRepository.findByEmail(email);
        log.info("plan isPresent : " + plan.isPresent());
        log.info("account isPresent : " + account.isPresent());
        Optional<Participant> participant;
        if (plan.isEmpty() || account.isEmpty()){
            log.info("plan.isEmpty()");
            participant = null;
            throw new RuntimeException("plan이 없습니다.");
        } else {
            participant = participantRepository.findByPlanAndAccount(plan.get(), account.get());
            log.info("participant isPresent : " + participant.isPresent());
        }

        try {
            participant.get().setTransactionMoney(money);
            participantRepository.save(participant.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("transaction_money 업데이트 및 저장에 실패했습니다.");
        }



    }
}