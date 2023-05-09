package com.dopamines.backend.plan.repository;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findById(Long planId);

}

