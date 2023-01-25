package com.clicktour.clicktour.service.planner;

import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.PlannerMap;
import com.clicktour.clicktour.domain.planner.dto.*;
import com.clicktour.clicktour.repository.PlannerMapRepository;
import com.clicktour.clicktour.repository.PlannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlannerService {
    private final PlannerRepository plannerRepository;
    private final PlannerMapRepository plannerMapRepository;

    @Transactional
    public PlannerSaveRequestDto save(PlannerSaveRequestDto plannerSaveRequestDto) {

        // planner 저장
        Planner planner =plannerRepository.save(plannerSaveRequestDto.toEntity());
        if (planner.getId() == null) {
            return null;
        }

        // planner map 저장
        for(PlannerMap plannerMap : plannerSaveRequestDto.getPlannerMapList()){
            PlannerMapSaveRequestDto plannerMapSaveRequestDto = new PlannerMapSaveRequestDto(plannerMap, planner);
            if (plannerMapRepository.save(plannerMapSaveRequestDto.toEntity()).getId() == null) {
                return null;
            }
        }

        return plannerSaveRequestDto;
    }

    @Transactional
    public PlannerDetailResponseDto findById(Long id) {
        Planner planner = plannerRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 플래너가 존재하지 않습니다. id :" + id));
        return new PlannerDetailResponseDto(planner);
    }

    @Transactional
    public List<PlannerResponseDto> findAllDesc(){
        return plannerRepository.findAllDesc().
                stream().map(PlannerResponseDto::new).
                collect(Collectors.toList());
    }

    @Transactional
    public PlannerUpdateRequestDto update(Long id, PlannerUpdateRequestDto requestDto){

        // 플래너 수정
        Planner planner = plannerRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 플래너가 존재하지 않습니다. id : " + id));
        planner.update(requestDto.getTitle(),requestDto.getStart_date(), requestDto.getEnd_date(),
                requestDto.getIntro());

        // 플랜 수정
        int plannerMapIndex = 0;

        for(PlannerMap plannerMap : requestDto.getPlannerMapList()){
            PlannerMap updatePlan = plannerMapRepository.findById(planner.getPlannerMapList().get(plannerMapIndex).getId()).
                    orElseThrow(() -> new IllegalArgumentException("해당 플래너가 존재하지 않습니다. id : " + id));

            updatePlan.update(plannerMap.getName(), plannerMap.getMemo(),
                    plannerMap.getDate(), plannerMap.getX(), plannerMap.getX());

            plannerMapIndex++;
        }

        return requestDto;
    }
}
