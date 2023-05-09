package com.dopamines.backend.review.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.service.UserService;
import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.plan.service.ParticipantService;
import com.dopamines.backend.plan.service.PlanService;
import com.dopamines.backend.review.dto.PhotoDto;
import com.dopamines.backend.review.entity.Photo;
import com.dopamines.backend.review.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PlanService planService;

    @Autowired
    UserService userService;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    ParticipantRepository participantRepository;

    // 인증을 사진을 등록하는 함수
    @Override
    public Long savePicture(Long planId, String file) {

        Plan plan = planService.getPlanById(planId);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Photo photo = Photo.builder()
                .plan(plan)
                .photoUrl(file)
                .registerTime(now)
                .build();
        photoRepository.save(photo);

        return photo.getPhotoId();

    }

    // 사진 있는지 확인
    @Override
    public boolean isPhotoRegistered(Long planId) {
        Plan plan = planService.getPlanById(planId);

        Optional<Photo> photo = photoRepository.findByPlan(plan);
        return photo.isPresent();
    }


    // 월별 사진 리스트를 가져오는 함수
    public List<Participant> getPhotosByMonthAndUser(String userEmail, LocalDate selectedMonth) {

        Account account = userService.findByEmail(userEmail);
        // 현재 사용자가 참여한 모든 약속 리스트를 가져옵니다.

        List<Participant> myParticipants = participantRepository.findByAccount(account);

        // 선택한 달의 시작일과 종료일을 계산합니다.
        LocalDate startOfMonth = selectedMonth.withDayOfMonth(1);
        LocalDate endOfMonth = selectedMonth.withDayOfMonth(selectedMonth.lengthOfMonth());
        List<Participant> participantList = participantRepository.findByAccountAndPlanPlanDateBetween(account, startOfMonth, endOfMonth);


        return participantList;


//        return photoDtoList;
    }
}
