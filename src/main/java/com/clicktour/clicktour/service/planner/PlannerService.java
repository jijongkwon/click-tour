package com.clicktour.clicktour.service.planner;

import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.dto.PlannerMapSaveRequestDto;
import com.clicktour.clicktour.domain.planner.dto.PlannerResponseDto;
import com.clicktour.clicktour.domain.planner.dto.PlannerSaveRequestDto;
import com.clicktour.clicktour.repository.PlannerMapRepository;
import com.clicktour.clicktour.repository.PlannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PlannerService {
    private final PlannerRepository plannerRepository;
    private final PlannerMapRepository plannerMapRepository;

    @Transactional
    public ResponseEntity<Planner> save(PlannerSaveRequestDto plannerSaveRequestDto) {

        // planner 저장
        Planner planner = plannerRepository.save(plannerSaveRequestDto.toEntity());
        if (planner == null) {
            return ResponseEntity.notFound().build();
        }

        // planner map 저장
        PlannerMapSaveRequestDto plannerMapSaveRequestDto = new PlannerMapSaveRequestDto(
                plannerSaveRequestDto.getPlannerMapList(), planner
        );

        if (plannerMapRepository.save(plannerMapSaveRequestDto.toEntity()).getId() == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public PlannerResponseDto findById(Long id) {
        Planner planner = plannerRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 플래너가 존재하지 않습니다. id :" + id));
        return new PlannerResponseDto(planner);
    }
}
