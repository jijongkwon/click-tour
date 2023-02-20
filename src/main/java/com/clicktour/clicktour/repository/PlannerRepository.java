package com.clicktour.clicktour.repository;

import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.dto.PlannerResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {
    @Query("SELECT p FROM Planner p ORDER BY p.id DESC")
    List<Planner> findAllDesc();

    Optional<Planner> findByUsersId(Long userId);
}
