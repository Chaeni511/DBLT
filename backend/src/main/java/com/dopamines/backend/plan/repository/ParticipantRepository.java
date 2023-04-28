package com.dopamines.backend.plan.repository;

import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    List<Participant> findByPlan(Plan plan);
    Optional<Participant> findByPlanAndUser(Plan plan, User user);

}
