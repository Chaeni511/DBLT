package com.dopamines.backend.plan.controller;

import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.service.ParticipantService;
import com.dopamines.backend.plan.service.PlanService;
import com.dopamines.backend.user.entity.User;
import com.dopamines.backend.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "plan", description = "약속을 관리하는 컨트롤러입니다.")
public class PlanController {

    @Autowired
    PlanService planService;

    @Autowired
    UserService userService;

    @Autowired
    ParticipantService participantService;


    @PostMapping("/create")
    @ApiOperation(value = "약속 생성 api 입니다.", notes = "약속 정보를 입력하여 약속을 생성합니다. 약속이 생성되면 PlanId를 반환합니다. participantIds는 유저id를 문자열로 입력합니다. planDate는 yyyy-MM-dd, planTime는 HH:mm:ss 의 형태로 입력합니다.")
    public ResponseEntity<Integer> createPlan(
            @RequestParam("userId") Integer userId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("planDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate planDate,
            @RequestParam("planTime") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime planTime,
            @RequestParam("location") String location,
            @RequestParam("find") Integer find,
            @RequestParam(value = "participantIds", required = false) String participantIdsStr // 입력값: 1,2,3,4
    ) {

        Integer planId = planService.createPlan(userId, title, description, planDate, planTime, location, find, participantIdsStr);
        return ResponseEntity.ok(planId);
    }


    @PutMapping("/update")
    @ApiOperation(value = "약속 수정 api 입니다.", notes = "PlanId를 입력하여 약속 정보를 불러와 약속 정보을 수정합니다. 약속이 생성되면 PlanId를 반환합니다. participantIds는 유저id를 문자열로 입력합니다. planDate는 yyyy-MM-dd, planTime는 HH:mm:ss 의 형태로 입력합니다.")
    public ResponseEntity<Void> updatePlan(
            @RequestParam("userId") Integer userId,
            @RequestParam("planId") Integer planId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("planDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate planDate,
            @RequestParam("planTime") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime planTime,
            @RequestParam("location") String location,
            @RequestParam("find") Integer find,
            @RequestParam(value = "participantIds", required = false) String participantIdsStr // 입력값: 1,2,3,4
    ) {

        try {
            planService.updatePlanAndParticipants(userId, planId, title, description, planDate, planTime, location, find, participantIdsStr);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


}