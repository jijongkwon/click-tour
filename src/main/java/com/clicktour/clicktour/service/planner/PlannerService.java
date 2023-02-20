package com.clicktour.clicktour.service.planner;

import com.clicktour.clicktour.domain.planner.Plan;
import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.dto.*;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.domain.users.dto.UserInfoResponseDto;
import com.clicktour.clicktour.repository.PlanRepository;
import com.clicktour.clicktour.repository.PlannerRepository;
import com.clicktour.clicktour.repository.UsersRepository;
import com.clicktour.clicktour.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlannerService {
    private final PlannerRepository plannerRepository;
    private final UsersRepository usersRepository;
    private final PlanRepository planRepository;
    private final UsersService usersService;

    @Transactional
    public PlannerSaveRequestDto savePlanner(PlannerSaveRequestDto plannerSaveRequestDto) {

        Optional<Users> users = usersRepository.findByNickname(plannerSaveRequestDto.getNickname());

        if(users.isEmpty()){
            return null;
        }

        plannerSaveRequestDto.setUsers(users.get());

        // planner 저장
        Planner planner = plannerRepository.save(plannerSaveRequestDto.toEntity());
        if (planner.getId() == null) {
            return null;
        }

        // plan 저장
        for (Plan plan : plannerSaveRequestDto.getPlanList()) {
            PlanSaveRequestDto planSaveRequestDto = new PlanSaveRequestDto(plan, planner);
            if (planRepository.save(planSaveRequestDto.toEntity()).getId() == null) {
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
    public List<PlannerResponseDto> findAllDesc() {
        return plannerRepository.findAllDesc().
                stream().map(PlannerResponseDto::new).
                collect(Collectors.toList());
    }

    @Transactional
    public List<PlannerResponseDto> findPlannerList(String jwtToken){
        UserInfoResponseDto userInfoResponseDto = usersService.getUserInfo(jwtToken);
        return plannerRepository.findByUsersId(userInfoResponseDto.getId()).
                stream().map(PlannerResponseDto::new).
                collect(Collectors.toList());
    }

    @Transactional
    public PlannerUpdateRequestDto updatePlanner(Long id, PlannerUpdateRequestDto requestDto) {

        /* 플래너 수정 */
        Planner planner = plannerRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 플래너가 존재하지 않습니다. id : " + id));
        planner.update(requestDto.getTitle(), requestDto.getStart_date(), requestDto.getEnd_date(),
                requestDto.getIntro());

        /* 플랜 수정 */
        for(Plan plan : requestDto.getPlanList()){
            // 플랜 추가가 있을 시
            if(plan.getId() == null){
                PlanSaveRequestDto planSaveRequestDto = new PlanSaveRequestDto(plan, planner);
                planRepository.save(planSaveRequestDto.toEntity());
            }
            if(plan.getId() != null){
                updatePlan(plan);
            }
        }

        /* 플랜 삭제 */
        for(Plan plan : planner.getPlanList()){
            if(!isIdInPlanner(plan, requestDto)){
                planDelete(plan.getId());
            }
        }

        return requestDto;
    }

    @Transactional
    public void updatePlan(Plan plan) {
        Plan updatePlan = planRepository.findById(plan.getId()).
                    orElseThrow(() -> new IllegalArgumentException("해당 플랜이 존재하지 않습니다."));

            updatePlan.update(plan.getName(), plan.getMemo(),
                    plan.getDate(), plan.getX(), plan.getY());
    }

    @Transactional
    public void plannerDelete(Long id) {
        Planner planner = plannerRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 플래너가 존재하지 않습니다. id : " + id));
        plannerRepository.delete(planner);
    }

    @Transactional
    public void planDelete(Long id){
        Plan plan = planRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 플랜이 존재하지 않습니다. id : " + id));
        planRepository.delete(plan);
    }

    public boolean isIdInPlanner(Plan plan, PlannerUpdateRequestDto requestDto) {
        for(Plan planRequestDto : requestDto.getPlanList()){
            if(planRequestDto.getId() == null){
                continue;
            }
            if(planRequestDto.getId().equals(plan.getId())){
                return true;
            }
        }
        return false;
    }
}
