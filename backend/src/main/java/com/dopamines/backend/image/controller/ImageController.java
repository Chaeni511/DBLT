package com.dopamines.backend.image.controller;


import com.dopamines.backend.image.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "test", description = "파일을 업로드 합니다.")
public class ImageController {

    @Autowired
    private final ImageService service;


    @PutMapping("/upload")
    @ApiOperation(value = "파일 업로드", notes = "파일을 업로드하고 img url을 가져옵니다. 폴더명은 상황에 맞게..")
    public ResponseEntity<Object> uploadAndGetLink(MultipartFile multipartFile, String folderName) throws Exception  {
        String url=service.saveFile(multipartFile,folderName);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

}
