package com.clicktour.clicktour.repository;

import com.clicktour.clicktour.domain.planner.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
