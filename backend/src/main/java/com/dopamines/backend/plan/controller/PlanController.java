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
    @ApiOperation(value = "약속 생성 api 입니다.", notes = "participantIds는 유저id를 문자열로 입력합니다. planDate는 yyyy-MM-dd, planTime는 HH:mm:ss 의 형태로 입력합니다.")
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

        User user = userService.findByUserId(userId);

        Plan plan = planService.savePlan(
                Plan.builder()
                        .user(user)
                        .title(title)
                        .description(description)
                        .planDate(planDate)
                        .planTime(planTime)
                        .location(location)
                        .find(find)
                        .status(0)
                        .build()
        );

        // 방장 참가자로 추가
        participantService.saveParticipant(
                Participant.builder()
                        .user(user)
                        .plan(plan)
                        .build()
        );
        log.info(user.getEmail(), "님이 참가되었습니다");

        // 참가자 추가
        // participantIdsStr 값이 들어오면 실행합니다.
        if (participantIdsStr != null && !participantIdsStr.isEmpty()) {
            String[] participantIds = participantIdsStr.split(",");
            for (String participantId : participantIds) {
                User participant = userService.findByUserId(Integer.valueOf(participantId));
                
                participantService.saveParticipant(
                        Participant.builder()
                                .user(participant)
                                .plan(plan)
                                .build()
                );
                log.info(participant.getEmail(), "님이 참가되었습니다.");
            }
        }

        return ResponseEntity.ok(plan.getPlanId());
    }
}
