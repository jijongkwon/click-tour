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
public class PlannerApiController {
    private final PlannerService plannerService;

    @PostMapping("/api/v1/planner/post")
    public ResponseEntity<PlannerSaveRequestDto> save(@RequestBody PlannerSaveRequestDto requestDto) {
        PlannerSaveRequestDto plannerSaveRequestDto = plannerService.save(requestDto);
        if (plannerSaveRequestDto == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<PlannerSaveRequestDto>(plannerSaveRequestDto, HttpStatus.OK);
    }

    @GetMapping("api/v1/planner/{id}")
    public ResponseEntity<PlannerDetailResponseDto> readDetail(@PathVariable Long id) {
        PlannerDetailResponseDto plannerResponseDto = plannerService.findById(id);
        if(plannerResponseDto == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<PlannerDetailResponseDto>(plannerResponseDto, HttpStatus.OK);
    }

    @GetMapping("api/v1/planner")
    public ResponseEntity<List<PlannerResponseDto>> readList(){
        List<PlannerResponseDto> plannerResponseDtoList = plannerService.findAllDesc();
        if(plannerResponseDtoList ==  null){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<List<PlannerResponseDto>>(plannerResponseDtoList, HttpStatus.OK);
    }

    @PutMapping("api/v1/planner/update/{id}")
    public ResponseEntity<PlannerUpdateRequestDto> update(@PathVariable Long id,
                                                           @RequestBody PlannerUpdateRequestDto requestDto){

        plannerService.update(id, requestDto);

        return new ResponseEntity<PlannerUpdateRequestDto>(requestDto, HttpStatus.OK);
    }

    @DeleteMapping("api/v1/planner/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        plannerService.delete(id);
        return new ResponseEntity<>("delete", HttpStatus.OK);
    }
}
