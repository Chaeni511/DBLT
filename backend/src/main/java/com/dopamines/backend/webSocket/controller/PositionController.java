package com.dopamines.backend.webSocket.controller;

import com.dopamines.backend.webSocket.dto.PlanRoomDto;
import com.dopamines.backend.webSocket.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/position")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(value = "webSocket", description = "webSocket 컨트롤러입니다.")
public class PositionController {

    private final PositionService positionService;

    @PostMapping("/create")
    @Operation(summary = "webSocket room 생성하는 api 입니다.", description = "각 약속의 planId를 활용하여 위치 정보를 연결할 소켓의 방을 만듭니다.")
    public PlanRoomDto createRoom(@RequestParam("planId") String planId){
        return positionService.createRoom(planId);
    }


    @GetMapping("/roomId")
    @Operation(summary = "webSocket의 모든 roomId 가져오는 api 입니다.", description = "소켓이 열려있는 모든 방을 가져옵니다.")
    public List<String> findAllRoomIds() {
        List<String> roomIds = new ArrayList<>();
        for (PlanRoomDto room : positionService.findAllRoom()) {
            roomIds.add(room.getRoomId());
        }
        return roomIds;
    }

    /////// 세션 못가져옴 /////////////////
//    @GetMapping("/room")
//    @Operation(summary = "WebSocket의 모든 room 가져오는 api 입니다.", description = "소켓이 열려있는 모든 방을 가져옵니다.")
//    public List<PlanRoomDto> findAllRooms(){
//        return positionService.findAllRoom();
//    }


}