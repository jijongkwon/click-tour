package com.clicktour.clicktour.repository;

import com.clicktour.clicktour.domain.planner.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
