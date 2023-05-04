package com.dopamines.backend.test.controller;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.dopamines.backend.test.dto.TestDto;
import com.dopamines.backend.test.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "test", description = "테스트 컨트롤러입니다.")
public class TestController {

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

    @GetMapping("/ftp")
    public List<Bucket> getBucketList(){
        final String endPoint = "https://kr.object.ncloudstorage.com";
        final String regionName = "kr-standard";
        final String accessKey = "jUWjiv3t6vIcjJ8Rrm13";
        final String secretKey = "FCgMJ5wLWkbqWNHVRa445darB3eZ92IvP43AsPe8";

// S3 client
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();

        try {
            List<Bucket> buckets = s3.listBuckets();
            System.out.println("Bucket List: ");
            for (Bucket bucket : buckets) {
                System.out.println("    name=" + bucket.getName() + ", creation_date=" + bucket.getCreationDate() + ", owner=" + bucket.getOwner().getId());
            }
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }
    }

}
