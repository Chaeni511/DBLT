package com.dopamines.backend.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String saveImage(MultipartFile file, String folderName) throws IOException;
}
