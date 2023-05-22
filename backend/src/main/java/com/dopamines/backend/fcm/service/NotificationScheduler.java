package com.dopamines.backend.fcm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationScheduler {

//    @Scheduled(cron = "0 * * * * ?") // 매일 자정에 동작해요
    public void pushPlanAlarm() {
        System.out.println("스케쥴러가 동작해요!");
    }

}
