package com.dopamines.backend.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public String saveFile(MultipartFile file,String folderName) throws IOException;


}
