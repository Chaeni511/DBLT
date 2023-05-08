package com.dopamines.backend.image.controller;

import com.dopamines.backend.common.util.ResponseFile;
import com.dopamines.backend.image.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "test", description = "파일을 업로드 합니다.")
public class FileController {

    @Autowired
    private final FileService service;

    ResponseFile response = new ResponseFile();
    @PostMapping("/upload")
    @ApiOperation(value = "파일 업로드", notes = "파일을 업로드하고 img url을 가져옵니다. 폴더명은 상황에 맞게..")
    public ResponseEntity<Object> uploadAndGetLink(MultipartFile multipartFile, String folderName) throws Exception  {
        String url=service.saveFile(multipartFile,folderName);
        return new ResponseEntity<Object>(response.makeSimpleRes(url), HttpStatus.OK);
    }

}