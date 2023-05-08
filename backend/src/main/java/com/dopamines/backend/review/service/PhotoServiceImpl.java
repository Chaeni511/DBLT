package com.dopamines.backend.review.service;

import com.dopamines.backend.review.entity.Photo;
import com.dopamines.backend.review.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {


    @Autowired
    PhotoRepository photoRepository;

    // 인증을 사진을 등록하는 함수
    @Override
    public Long savePicture(Photo photo) {
        photoRepository.save(photo);
        return photo.getPhotoId();
    }

}
