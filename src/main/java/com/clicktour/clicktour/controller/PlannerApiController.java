package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.common.message.dto.ResponseDto;
import com.clicktour.clicktour.common.message.enums.ErrorMessage;
import com.clicktour.clicktour.common.message.dto.ExceptionDto;
import com.clicktour.clicktour.common.message.enums.SuccessMessage;
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
@RequestMapping("/api/v1/planner")
public class PlannerApiController {
    private final PlannerService plannerService;

    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody PlannerSaveRequestDto requestDto) {
        PlannerSaveRequestDto plannerSaveRequestDto = plannerService.savePlanner(requestDto);
        if (plannerSaveRequestDto == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_POST_PLANNER), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlannerDetailResponseDto> readDetail(@PathVariable Long id) {
        PlannerDetailResponseDto plannerResponseDto = plannerService.findById(id);
        if(plannerResponseDto == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(plannerResponseDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<PlannerResponseDto>> readList(HttpServletRequest httpServletRequest){
        String jwtToken = httpServletRequest.getHeader("X-AUTH-TOKEN");
        List<PlannerResponseDto> plannerResponseDtoList = plannerService.findIndividualPlannerList(jwtToken);
        if(plannerResponseDtoList ==  null){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<List<PlannerResponseDto>>(plannerResponseDtoList, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PlannerUpdateRequestDto requestDto){

        plannerService.updatePlanner(id, requestDto);

        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_UPDATE_PLANNER), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        plannerService.plannerDelete(id);
        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_DELETE_PLANNER), HttpStatus.OK);
    }

    @PostMapping("/recommend")
    public ResponseEntity<?> recommendPlanner(@RequestBody PlannerRecommendRequestDto recommendRequestDto){
        PlannerDetailResponseDto plannerResponseDto = plannerService.recommendPlanner(recommendRequestDto);
        if(plannerResponseDto == null){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_PLANNER), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(plannerResponseDto, HttpStatus.OK);
    }

    @GetMapping("/visible")
    public ResponseEntity<List<PlannerResponseDto>> readVisiblePlannerList(HttpServletRequest httpServletRequest){
        String jwtToken = httpServletRequest.getHeader("X-AUTH-TOKEN");
        List<PlannerResponseDto> plannerResponseDtoList = plannerService.findVisiblePlannerList(jwtToken);
        if(plannerResponseDtoList ==  null){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(plannerResponseDtoList, HttpStatus.OK);
    }
}
