package com.dopamines.backend.plan.controller;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.service.UserService;
import com.dopamines.backend.plan.dto.PlanDto;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.plan.service.ParticipantService;
import com.dopamines.backend.plan.service.PlanService;
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

    @Autowired
    PlanRepository planRepository;


    @PostMapping("/create")
    @ApiOperation(value = "약속 생성 api 입니다.", notes = "약속 정보를 입력하여 약속을 생성합니다. 약속이 생성되면 PlanId를 반환합니다. participantIds는 유저id를 문자열로 입력합니다. planDate는 yyyy-MM-dd, planTime는 HH:mm:ss 의 형태로 입력합니다.")
    public ResponseEntity<Long> createPlan(
            @RequestParam("accountId") Long accountId,
            @RequestParam("title") String title,
            @RequestParam("planDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate planDate,
            @RequestParam("planTime") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime planTime,
            @RequestParam("location") String location,
            @RequestParam("find") Integer find,
            @RequestParam(value = "participantIds", required = false) String participantIdsStr // 입력값: 1,2,3,4
    ) {

        if (!planService.isValidAppointmentTime(planDate, planTime)) {
//            log.warn("생성 실패: 약속 시간은 현재 시간으로부터 30분 이후여야 합니다.");
            log.warn("생성 실패: 약속 시간은 현재 시간 최소 1분전에 생성할 수 있습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Long planId = planService.createPlan(accountId, title, planDate, planTime, location, find, participantIdsStr);
        log.info("약속이 생성되었습니다.");
        return ResponseEntity.ok(planId);
    }


    @PutMapping("/update")
    @ApiOperation(value = "약속 수정 api 입니다.", notes = "PlanId를 입력하여 약속 정보를 불러와 약속 정보을 수정합니다. 약속이 생성되면 PlanId를 반환합니다. participantIds는 유저id를 문자열로 입력합니다. planDate는 yyyy-MM-dd, planTime는 HH:mm:ss 의 형태로 입력합니다.")
    public ResponseEntity<Void> updatePlan(
            @RequestParam("accountId") Long accountId,
            @RequestParam("planId") Long planId,
            @RequestParam("title") String title,
            @RequestParam("planDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate planDate,
            @RequestParam("planTime") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime planTime,
            @RequestParam("location") String location,
            @RequestParam("find") Integer find,
            @RequestParam(value = "participantIds", required = false) String participantIdsStr // 입력값: 1,2,3,4
    ) {

        try {
            Plan plan = planService.getPlanById(planId);

            Account account = userService.findByAccountId(accountId);

            // 방장 여부 확인
            if (!participantService.findIsHostByPlanAndUser(plan, account)) {
                log.warn("방장이 아닙니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            // 약속 상태 확인
            if (plan.getStatus() > 1) {
                log.warn("약속이 진행 중 이므로 수정할 수 없습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            if (!planService.isValidAppointmentTime(plan.getPlanDate(), plan.getPlanTime())) {
//                log.warn("수정 실패: 약속 시간은 현재 시간으로부터 30분 이후여야 합니다.");
                log.warn("수정 실패: 약속 시간은 현재 시간 최소 1분전에 수정할 수 있습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            planService.updatePlanAndParticipant(plan, title, planDate, planTime, location, find, participantIdsStr);
            log.info("planId {}이고 title '{}'인 약속이 방장 {}에 의해 수정되었습니다.", planId, title, account.getNickname());

            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error("수정 실패: planId {}는 존재하지 않는 planId 입니다.", planId, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            // 기타 예외 발생 시, HttpStatus.INTERNAL_SERVER_ERROR 반환
            log.error("수정 실패: planId {}", planId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @DeleteMapping("/delete")
    @ApiOperation(value = "약속 삭제 api 입니다.", notes = "수정하려는 accountId와 PlanId를 입력하여 약속 정보를 삭제합니다.")
    public ResponseEntity<Void> deletePlan(
            @RequestParam("accountId") Long accountId,
            @RequestParam("planId") Long planId
    ){

        try {
            Plan plan = planService.getPlanById(planId);

            Account account = userService.findByAccountId(accountId);

            // 방장 여부 확인
            if (!participantService.findIsHostByPlanAndUser(plan, account)) {
                log.warn("방장이 아닙니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            // 약속 상태 확인
            if (plan.getStatus() > 1) {
                log.warn("약속이 진행 중 이므로 삭제할 수 없습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            planService.deletePlan(plan);

            log.info("PlanId {}인 약속이 방장 {}에 의해 삭제되었습니다.", planId, account.getNickname());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            // PlanId가 잘못된 경우, HttpStatus.BAD_REQUEST 반환
            log.error("삭제 실패: planId {}는 존재하지 않는 planId 입니다.", planId, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            // 기타 예외 발생 시, HttpStatus.INTERNAL_SERVER_ERROR 반환
            log.error("삭제 실패: planId {}", planId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/detail")
    @ApiOperation(value = "약속 상세 정보를 불러오는 api 입니다.", notes = "PlanId를 입력하여 약속 상세 정보를 불러옵니다. designation은 칭호이며 0 보통, 1 일찍, 2 지각을 나타냅니다.")
    public ResponseEntity<PlanDto> planDetail(@RequestParam("planId") Long planId) {

        PlanDto planDto = planService.getPlanDetail(planId);
        return new ResponseEntity<>(planDto, HttpStatus.OK);
    }

//    @GetMapping("/list")
//    @ApiOperation(value = "약속 리스트를 불러오는 api 입니다.", notes = "userId와 planDate를 입력하여 유저의 해당 날짜 약속 리스트를 불러옵니다.")
//    public ResponseEntity<PlanDto> planDetail(
//            @RequestParam("userId") Integer userId,
//            @RequestParam("planDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate planDate) {


//        return new ResponseEntity<>(planDto, HttpStatus.OK);
//    }

}