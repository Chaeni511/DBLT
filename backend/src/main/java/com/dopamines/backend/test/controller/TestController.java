package com.dopamines.backend.test.controller;

import com.dopamines.backend.test.dto.TestDto;
import com.dopamines.backend.test.service.TestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "test", description = "테스트 컨트롤러에요!")
public class TestController {

    private final TestService teatservice;

    //db와 상호작용 필요없는 일반 api
    @GetMapping("/hello")
    @ApiOperation(value = "헬로우 월드", notes = "헬로우 월드!!!!") //notes는 안적어도 상관없어요
    public String Hello(){
        return "hello world";
    }


    //jpa가 기본적으로 제공하는 함수를 이용해 db와 상호작용
    @GetMapping("/testDefault")
    @ApiOperation(value = "jpa 기본 동작 확인", notes = "테스트 테이블의 칼럼 수를 반환한다.")
    public long getCount(){
        return teatservice.getCount();
    }


    //jpa가 기본적으로 제공하지 않지만, 비교적 간단한 쿼리문인 경우
    @GetMapping("/testCustom")
    @ApiOperation(value = "jpa 동작 확인", notes = "이름에 '안녕'을 포함하는 칼럼 리스트를 반환")
    public List<TestDto> getTest(){
        return teatservice.getCustom("안녕");
    }

}
