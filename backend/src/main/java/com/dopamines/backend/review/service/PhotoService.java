package com.dopamines.backend.review.service;

import com.dopamines.backend.review.dto.PhotoDateDto;
import com.dopamines.backend.review.dto.PhotoDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PhotoService {
    Long savePicture(Long planId, String pictureUrl);

    boolean isPhotoRegistered(Long planId);

    List<PhotoDto> getPhotosByMonthAndUser(String userEmail, LocalDate selectedDate);

    Map<LocalDate, List<PhotoDateDto>> getPhotosByDateMap(String userEmail, LocalDate selectedDate);

}
