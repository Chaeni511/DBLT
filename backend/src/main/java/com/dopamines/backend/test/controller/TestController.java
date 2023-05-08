package com.dopamines.backend.test.controller;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dopamines.backend.test.dto.ObjectDto;
import com.dopamines.backend.test.dto.TestDto;
import com.dopamines.backend.test.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "test", description = "테스트 컨트롤러입니다.")
public class TestController {
//    @Value("${cloud.aws.credentials.accessKey}")
//    private String accessKey;
//    @Value("${cloud.aws.credentials.secretKey}")
//    private String secretKey;
//    @Value("${cloud.aws.s3.endpoint}")
//    private String endPoint;
//    @Value("${cloud.aws.region.static}")
//    private String regionName;

    private Logger log = LoggerFactory.getLogger(TestController.class);

    private final TestService teatservice;

    //db와 상호작용 필요없는 일반 api
    @GetMapping("/hello")
    @ApiOperation(value = "hello world 출력", notes = "hello world 반환") //notes는 안적어도 상관없음!
    public String Hello(){
        return "hello world!";
    }

    
    //jpa가 기본적으로 제공하는 함수를 이용해 db와 상호작용 (post)
    @PostMapping("/testPost")
    @ApiOperation(value = "Jpa 기본 동작 확인", notes = "데이터베이스에 데이터 삽입")
    public ResponseEntity<Void> postData(@RequestParam("name") String name) {
        teatservice.saveData(name);
        return ResponseEntity.ok().build();
    }


    //jpa가 기본적으로 제공하는 함수를 이용해 db와 상호작용 (get)
    @GetMapping("/testDefault")
    @ApiOperation(value = "jpa 기본 동작 확인", notes = "테스트 테이블의 칼럼 수 반환")
    public long getCount(){
        return teatservice.getCount();
    }


    //jpa가 기본적으로 제공하지 않지만, dto 이용
    @GetMapping("/testCustom")
    @ApiOperation(value = "jpa dto 동작 확인", notes = "이름에 '안녕'을 포함하는 칼럼 리스트 반환")
    public List<TestDto> getTest(){
        return teatservice.getCustom("안녕");
    }
//
//    @GetMapping("/ftp")
//    public List<Bucket> getBucketList(){

// S3 client
//        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
//                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
//                .build();
//        log.info("endPoint: "+ endPoint.toString() + ", accessKey: "+ accessKey + ", secretKey: "+ secretKey);
//        log.info("TestController의 getBucketList에서 찍는 s3: " + s3);
//
//        try {
//            List<Bucket> buckets = s3.listBuckets();
//            log.info("TestController의 getBucketList에서 찍는 buckets: " + buckets);
//
//            System.out.println("Bucket List: ");
//            for (Bucket bucket : buckets) {
//                System.out.println("    name=" + bucket.getName() + ", creation_date=" + bucket.getCreationDate() + ", owner=" + bucket.getOwner().getId());
//            }
//            return buckets;
//
//        } catch (AmazonS3Exception e) {
//            e.printStackTrace();
//        } catch(SdkClientException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @PostMapping("/upload")
//    public void uploadFile(){
//        // S3 client
//        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
//                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
//                .build();
//
//        String bucketName = "dlt";
//
//// create folder
//        String folderName = "test/";
//
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(0L);
//        objectMetadata.setContentType("application/x-directory");
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName, new ByteArrayInputStream(new byte[0]), objectMetadata);
//
//        try {
//            s3.putObject(putObjectRequest);
//            System.out.format("Folder %s has been created.\n", folderName);
//        } catch (AmazonS3Exception e) {
//            e.printStackTrace();
//        } catch(SdkClientException e) {
//            e.printStackTrace();
//        }
//
//// upload local file
//        String objectName = "sample-object";
//        String filePath = "/Users/ichaeeun/Downloads/198722804_1_1663929902_w180.jpg";
//
//        try {
//            s3.putObject(bucketName, objectName, new File(filePath));
//            System.out.format("Object %s has been created.\n", objectName);
//        } catch (AmazonS3Exception e) {
//            e.printStackTrace();
//        } catch(SdkClientException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @GetMapping("/get")
//    public ObjectDto getObjectTest(){
//        ObjectDto objectDto = new ObjectDto(11L, "getObjectTest");
//
//        return objectDto;
//    }
//    @PostMapping("/post")
//    public Map<String, List<ObjectDto>> postObjectTest(String name){
//        if(name == null){
//            log.info("name이 null임");
//        } else {
//            log.info("name: " + name);
//        }
//
//        Map<String, List<ObjectDto>> res = new HashMap<>();
//        res.put("res", new ArrayList<ObjectDto>());
//        for(long i = 0; i < 10L; i++) {
//            res.get("res").add(new ObjectDto(i, name + i));
//        }
//        return res;
//    }

}
