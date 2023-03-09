package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.common.message.dto.ResponseDto;
import com.clicktour.clicktour.common.message.enums.ErrorMessage;
import com.clicktour.clicktour.common.message.dto.ExceptionDto;
import com.clicktour.clicktour.common.message.enums.SuccessMessage;
import com.clicktour.clicktour.common.service.CheckService;
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
    private final CheckService checkService;

    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody PlannerSaveRequestDto requestDto, HttpServletRequest httpServletRequest) {

        System.out.println("1");
        if(checkService.checkJwtToken(httpServletRequest.getHeader("X-AUTH-TOKEN"))){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.FORBIDDEN), HttpStatus.FORBIDDEN);
        }

        PlannerSaveRequestDto plannerSaveRequestDto = plannerService.savePlanner(requestDto);
        if (plannerSaveRequestDto == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_POST_PLANNER), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readDetail(@PathVariable Long id) {
        PlannerDetailResponseDto plannerResponseDto = plannerService.findById(id);
        if(plannerResponseDto == null){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_PLANNER), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(plannerResponseDto, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> readList(HttpServletRequest httpServletRequest){
        String jwtToken = httpServletRequest.getHeader("X-AUTH-TOKEN");
        List<PlannerResponseDto> plannerResponseDtoList = plannerService.findIndividualPlannerList(jwtToken);
        if(plannerResponseDtoList.isEmpty()){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_PLANNER), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(plannerResponseDtoList, HttpStatus.OK);
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
    public ResponseEntity<?> readVisiblePlannerList(){
        List<PlannerResponseDto> plannerResponseDtoList = plannerService.findVisiblePlannerList();
        if(plannerResponseDtoList.isEmpty()){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_PLANNER), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(plannerResponseDtoList, HttpStatus.OK);
    }
}
