package com.clicktour.clicktour.repository;

import com.clicktour.clicktour.domain.planner.PlannerMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannerMapRepository extends JpaRepository<PlannerMap, Long> {
}
