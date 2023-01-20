package com.clicktour.clicktour.repository;

import com.clicktour.clicktour.domain.planner.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {
    @Query("SELECT p FROM Planner p ORDER BY p.id DESC")
    List<Planner> findAllDesc();
}
