package com.dopamines.backend.review.controller;

import com.dopamines.backend.image.service.FileService;
import com.dopamines.backend.image.service.FileServiceImpl;
import com.dopamines.backend.review.entity.Photo;
import com.dopamines.backend.review.service.PhotoService;
import com.dopamines.backend.review.service.PhotoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "photo", description = "약속 사진을 관리하는 컨트롤러입니다.")
public class PhotoController {

    @Autowired
    FileService fileService;

    @Autowired
    PhotoService photoService;


    @PostMapping("/registerPicture")
    @ApiOperation(value = "인증 사진을 등록하는 api입니다.", notes = "planId와 pictureUrl 활용하여 결과 값으로 photoId를 반환합니다.")
    public ResponseEntity<Long> savePicture(
            HttpServletRequest request,
            @RequestParam("planId") Long planId,
            @RequestParam("pictureUrl") MultipartFile file
    ) {
        // 사진 업로드
        String url = null;
        try {
            url = fileService.saveFile(file, "photo");
        } catch (IOException e) {
            log.info("사진 업로드 실패");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }


        Long photoId = photoService.savePicture(
                Photo.builder()
                        .photo(url)
                        .build()
        );
        return ResponseEntity.ok(photoId);
    }



}
