package com.dopamines.backend.plan.repository;

import com.dopamines.backend.plan.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
