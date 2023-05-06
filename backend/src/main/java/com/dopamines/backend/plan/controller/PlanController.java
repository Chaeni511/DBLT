package com.dopamines.backend.plan.controller;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.service.UserService;
import com.dopamines.backend.plan.dto.EndPlanDto;
import com.dopamines.backend.plan.dto.PlanDto;
import com.dopamines.backend.plan.dto.PlanListDto;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.plan.service.ParticipantService;
import com.dopamines.backend.plan.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    @Operation(summary = "약속 생성 api 입니다.", description = "약속 정보를 입력하여 약속을 생성합니다. 약속이 생성되면 PlanId을 Long 타입으로 반환합니다.<br>"  +
            "participantIds는 유저id를 문자열(예시 : 1,2,3,4,5)로 입력합니다. planDate는 yyyy-MM-dd, planTime는 HH:mm:ss 의 형태로 입력합니다.")
    public ResponseEntity<Long> createPlan(
            HttpServletRequest request,
            @RequestParam("title") String title,
            @RequestParam("planDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate planDate,
            @RequestParam("planTime") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime planTime,
            @RequestParam("location") String location,
            @RequestParam("find") Integer find,
            @RequestParam(value = "participantIds", required = false) String participantIdsStr // 입력값: 1,2,3,4
    ) {
        // 헤더에서 유저 이메일 가져옴
        String userEmail = request.getRemoteUser();

        if (planService.getTimeMinutesDifference(planDate, planTime) <= 0) {
            log.warn("생성 실패: 약속 시간은 현재 시간 이후 시간으로 생성할 수 있습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Long planId = planService.createPlan(userEmail, title, planDate, planTime, location, find, participantIdsStr);
        log.info("약속이 생성되었습니다.");
        return ResponseEntity.ok(planId);
    }


    @PutMapping("/update")
    @Operation(summary = "약속 수정 api 입니다.", description = "PlanId를 입력하여 약속 정보를 불러와 약속 정보을 수정합니다. 약속이 생성되면 PlanId를 반환합니다.<br>" +
            "participantIds는 유저id를 문자열(예시 : 1,2,3,4,5)로 입력합니다. planDate는 yyyy-MM-dd, planTime는 HH:mm:ss 의 형태로 입력합니다.")
    public ResponseEntity<Void> updatePlan(
            HttpServletRequest request,
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

            String userEmail = request.getRemoteUser();

            Account account = userService.findByEmail(userEmail);

            // 약속 상태 변경
            planService.updatePlanStatus(plan);

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

            if (planService.getTimeMinutesDifference(planDate, planTime) <= 0) {
                log.warn("수정 실패: 약속 시간은 현재 시간 이후 시간으로 변경할 수 있습니다.");
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
    @Operation(summary = "약속 삭제 api 입니다.", description = "PlanId를 입력하여 약속 정보를 삭제합니다. 방장만 가능합니다.")
    public ResponseEntity<Void> deletePlan(
            HttpServletRequest request,
            @RequestParam("planId") Long planId
    ){

        try {

            Plan plan = planService.getPlanById(planId);

            String userEmail = request.getRemoteUser();
            Account account = userService.findByEmail(userEmail);

            // 약속 상태 변경
            planService.updatePlanStatus(plan);

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
    @Operation(summary = "진행 중인 약속 상세 정보를 불러오는 api 입니다.", description = "PlanId를 입력하여 약속 상세 정보를 불러옵니다.<br>" +
            "designation은 칭호이며 0 보통, 1 일찍, 2 지각을 나타냅니다. status는 0 기본, 1 위치공유(30분 전~약속시간), 2 게임 활성화(약속시간~1시간 후), 3 약속 종료(1시간 이후)을 나타냅니다.")
    public ResponseEntity<PlanDto> planDetail(@RequestParam("planId") Long planId) {

        PlanDto planDto = planService.getPlanDetail(planId);
        return new ResponseEntity<>(planDto, HttpStatus.OK);
    }

    @GetMapping("/list")
    @Operation(summary = "약속 리스트를 불러오는 api 입니다.", description = "userId와 planDate를 입력하여 유저의 해당 날짜 약속 리스트를 불러옵니다.<br>" +
            "status는 0 기본, 1 위치공유(30분 전~약속시간), 2 게임 활성화(약속시간~1시간 후), 3 약속 종료(1시간 이후)을 나타냅니다. diff시간이 음수이면 약속 시간이 지났음을, 양수이면 약속 시간이 아직 남아 있음을 나타냅니다.")
    public ResponseEntity<List<PlanListDto>> planList(
            HttpServletRequest request,
            @RequestParam("planDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate planDate) {

        String userEmail = request.getRemoteUser();
        List<PlanListDto> planListDto = planService.getPlanList(userEmail, planDate);
        return new ResponseEntity<>(planListDto, HttpStatus.OK);
    }

    @GetMapping("/endDetail")
    @Operation(summary = "완료된 약속 상세 정보를 불러오는 api 입니다.", description = "PlanId를 입력하여 약속 상세 정보를 불러옵니다.<br>" +
            "designation은 칭호이며 0 보통, 1 일찍, 2 지각을 나타냅니다. status는 0 기본, 1 위치공유(30분 전~약속시간), 2 게임 활성화(약속시간~1시간 후), 3 약속 종료(1시간 이후)을 나타냅니다.")
    public ResponseEntity<EndPlanDto> endPlanDetail(
            HttpServletRequest request,
            @RequestParam("planId") Long planId
    ) {
        String userEmail = request.getRemoteUser();
        EndPlanDto endPlanDto = planService.getEndPlanDetail(planId, userEmail);
        return new ResponseEntity<>(endPlanDto, HttpStatus.OK);
    }

}