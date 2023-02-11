package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.domain.planner.dto.PlannerResponseDto;
import com.clicktour.clicktour.domain.planner.dto.PlannerDetailResponseDto;
import com.clicktour.clicktour.domain.planner.dto.PlannerSaveRequestDto;
import com.clicktour.clicktour.domain.planner.dto.PlannerUpdateRequestDto;
import com.clicktour.clicktour.service.planner.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PlannerApiController {
    private final PlannerService plannerService;

    @PostMapping("/planner/post")
    public ResponseEntity<PlannerSaveRequestDto> save(@RequestBody PlannerSaveRequestDto requestDto) {
        PlannerSaveRequestDto plannerSaveRequestDto = plannerService.savePlanner(requestDto);
        if (plannerSaveRequestDto == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<PlannerSaveRequestDto>(plannerSaveRequestDto, HttpStatus.OK);
    }

    @GetMapping("/planner/{id}")
    public ResponseEntity<PlannerDetailResponseDto> readDetail(@PathVariable Long id) {
        PlannerDetailResponseDto plannerResponseDto = plannerService.findById(id);
        if(plannerResponseDto == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<PlannerDetailResponseDto>(plannerResponseDto, HttpStatus.OK);
    }

    @GetMapping("/planner")
    public ResponseEntity<List<PlannerResponseDto>> readList(){
        List<PlannerResponseDto> plannerResponseDtoList = plannerService.findAllDesc();
        if(plannerResponseDtoList ==  null){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<List<PlannerResponseDto>>(plannerResponseDtoList, HttpStatus.OK);
    }

    @PutMapping("/planner/update/{id}")
    public ResponseEntity<PlannerUpdateRequestDto> update(@PathVariable Long id,
                                                           @RequestBody PlannerUpdateRequestDto requestDto){

        plannerService.updatePlanner(id, requestDto);

        return new ResponseEntity<PlannerUpdateRequestDto>(requestDto, HttpStatus.OK);
    }

    @DeleteMapping("/planner/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        plannerService.plannerDelete(id);
        return new ResponseEntity<>("delete", HttpStatus.OK);
    }
}
