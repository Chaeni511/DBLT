package com.dopamines.backend.review.service;

import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.review.dto.PhotoDto;

import java.time.LocalDate;
import java.util.List;

public interface PhotoService {
    Long savePicture(Long planId, String pictureUrl);

    boolean isPhotoRegistered(Long planId);

    List<PhotoDto> getPhotosByMonthAndUser(String userEmail, LocalDate selectedDate);

}
