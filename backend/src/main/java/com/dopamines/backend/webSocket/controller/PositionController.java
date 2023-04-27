package com.dopamines.backend.webSocket.controller;

import com.dopamines.backend.webSocket.dto.PlanRoomDto;
import com.dopamines.backend.webSocket.service.PositionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/position")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "WebSocket", description = "WebSocket 컨트롤러입니다.")
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public PlanRoomDto createRoom(@RequestParam("planId") String planId){
        return positionService.createRoom(planId);
    }


    @GetMapping
    public List<PlanRoomDto> findAllRooms(){
        return positionService.findAllRoom();
    }

//    Spring Security를 적용하여 User 클래스를 상속받은 CustomUser 클래스의 정보(로그인한 ID)를 Model에 담아 보낸다.
//    @GetMapping("/position")
//    public void position(Model model) {
//
//        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//
//        log.info("==================================");
//        log.info("@PositionController, GET position / Username : " + user.getUsername());
//
//        model.addAttribute("userid", user.getUsername());
//    }

}