package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.dto.PlannerResponseDto;
import com.clicktour.clicktour.domain.planner.dto.PlannerSaveRequestDto;
import com.clicktour.clicktour.service.planner.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PlannerApiController {
    private final PlannerService plannerService;

    @PostMapping("/api/v1/planner")
    public ResponseEntity<Planner> save(@RequestBody PlannerSaveRequestDto requestDto){
        if(plannerService.save(requestDto) == 0){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/v1/planner/{id}")
    public PlannerResponseDto read(@PathVariable Long id){
        return plannerService.findById(id);
    }
}
