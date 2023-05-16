package com.dopamines.backend.fcm.repository;

import com.dopamines.backend.fcm.entity.FCM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FCMRepository extends JpaRepository<FCM, Long> {
}
