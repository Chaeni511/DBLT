package com.dopamines.backend.plan.controller;

import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.plan.service.ParticipantService;
import com.dopamines.backend.plan.service.PlanService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/participant")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "participant", description = "약속을 관리하는 컨트롤러입니다.")
public class ParticipantController {

    @Autowired
    ParticipantService participantService;

    @Autowired
    PlanService planService;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @GetMapping("/{planId}")
    public ResponseEntity<List<Participant>> getPlanById(@PathVariable Integer planId) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Plan ID"));
        List<Participant> participant = participantRepository.findByPlan(plan);
        return ResponseEntity.ok(participant);
    }
}
