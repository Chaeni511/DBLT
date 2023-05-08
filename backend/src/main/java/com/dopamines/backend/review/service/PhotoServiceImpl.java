package com.dopamines.backend.review.service;

import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.service.PlanService;
import com.dopamines.backend.review.entity.Photo;
import com.dopamines.backend.review.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PlanService planService;

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

}
