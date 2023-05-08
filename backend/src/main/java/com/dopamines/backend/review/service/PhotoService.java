package com.dopamines.backend.review.service;

import com.dopamines.backend.review.entity.Photo;

public interface PhotoService {
    Long savePicture(Long planId, String pictureUrl);

    boolean isPhotoRegistered(Long planId);

}
