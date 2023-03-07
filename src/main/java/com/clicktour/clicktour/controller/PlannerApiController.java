package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.common.message.enums.ErrorMessage;
import com.clicktour.clicktour.common.message.dto.ExceptionDto;
import com.clicktour.clicktour.domain.planner.dto.*;
import com.clicktour.clicktour.service.planner.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        return new ResponseEntity<PlannerSaveRequestDto>(requestDto, HttpStatus.OK);
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
    public ResponseEntity<List<PlannerResponseDto>> readList(HttpServletRequest httpServletRequest){
        String jwtToken = httpServletRequest.getHeader("X-AUTH-TOKEN");
        List<PlannerResponseDto> plannerResponseDtoList = plannerService.findPlannerList(jwtToken);
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

    @PostMapping("planner/recommend")
    public ResponseEntity<?> recommendPlanner(@RequestBody PlannerRecommendRequestDto recommendRequestDto){
        PlannerDetailResponseDto plannerResponseDto = plannerService.recommendPlanner(recommendRequestDto);
        if(plannerResponseDto == null){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_PLANNER), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PlannerDetailResponseDto>(plannerResponseDto, HttpStatus.OK);
    }
}
