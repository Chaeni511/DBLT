package com.dopamines.backend.review.controller;

import com.dopamines.backend.image.service.ImageService;
import com.dopamines.backend.plan.service.ParticipantService;
import com.dopamines.backend.plan.service.PlanService;
import com.dopamines.backend.review.dto.PhotoDto;
import com.dopamines.backend.review.entity.Photo;
import com.dopamines.backend.review.service.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "photo", description = "약속 사진을 관리하는 컨트롤러입니다.")
public class PhotoController {

    @Autowired
    ImageService imageService;

    @Autowired
    PhotoService photoService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    PlanService planService;


    @PostMapping("/register")
    @ApiOperation(value = "인증 사진을 등록하는 api입니다.", notes = "planId와 photoFile 활용하여 결과 값으로 photoId를 반환합니다. photoFile은 MultipartFile로 받아옵니다.")
    public ResponseEntity<Long> savePhoto(
            HttpServletRequest request,
            @RequestParam("planId") Long planId,
            @RequestParam("photoFile") MultipartFile file
    ) {

        String userEmail = request.getRemoteUser();

        // 참가자 인지 확인
        if (!planService.isMyPlan(userEmail, planId)) {
            log.info("참가자가 아닙니다. userEmail: {}, planId: {}", userEmail, planId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // 사진있는지 확인
        if (photoService.isPhotoRegistered(planId)) {
            log.info("이미 등록된 사진입니다. planId: {}", planId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // 사진 업로드
        String url = null;
        try {
            url = imageService.saveFile(file, "photo");
        } catch (IOException e) {
            log.error("사진 업로드 실패", e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        Long photoId = photoService.savePicture(planId, url);

        return ResponseEntity.ok(photoId);
    }

    @PostMapping("/list")
    @ApiOperation(value = "갤러리에 사진 내역을 가져오는 api입니다.", notes = "month를 활용하여 해당 월의 사진 정보를 가져옵니다.<br/> 'yyyy-MM'의 형태로 month를 입력합니다.")
    public ResponseEntity<List<PhotoDto>> getPhotoList(
            HttpServletRequest request,
            @RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM") LocalDate month
    ) {

        String userEmail = request.getRemoteUser();
        List<PhotoDto> photoList = photoService.getPhotosByMonthAndUser(userEmail, month);

        return ResponseEntity.ok(photoList);
    }

}
