package com.dopamines.backend.plan.repository;

import com.dopamines.backend.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findById(Integer planId);
}

