package com.dopamines.backend.plan.repository;

import com.dopamines.backend.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlanRepository extends JpaRepository<Plan, Long> {
}
