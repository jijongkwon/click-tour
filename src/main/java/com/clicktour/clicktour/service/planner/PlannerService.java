package com.clicktour.clicktour.service.planner;

import com.clicktour.clicktour.domain.planner.Place;
import com.clicktour.clicktour.domain.planner.Plan;
import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.dto.*;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.domain.users.dto.UserInfoResponseDto;
import com.clicktour.clicktour.repository.PlaceRepository;
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
    private final PlaceRepository placeRepository;
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

        // place 저장
        for (Place place : plannerSaveRequestDto.getPlaceList()){
            PlaceSaveRequestDto placeSaveRequestDto = new PlaceSaveRequestDto(place, planner);
            if(placeRepository.save(placeSaveRequestDto.toEntity()).getId() == null){
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

        System.out.println(requestDto.getConcept());
        /* 플래너 수정 */
        Planner planner = plannerRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 플래너가 존재하지 않습니다. id : " + id));
        planner.update(requestDto.getTitle(), requestDto.getStart_date(), requestDto.getEnd_date(),
                requestDto.getIntro(), requestDto.getConcept());

        /* 플랜 수정 */
        updatePlan(requestDto, planner);

        /* 플랜 삭제 */
        planDelete(requestDto, planner);

        /* 플레이스 삭제 */
        placeDelete(planner);

        /* 플레이스 수정 */
        updatePlace(requestDto, planner);


        return requestDto;
    }

    @Transactional
    public void updatePlan(PlannerUpdateRequestDto requestDto, Planner planner) {
        for(Plan plan : requestDto.getPlanList()){
            PlanSaveRequestDto planSaveRequestDto = new PlanSaveRequestDto(plan, planner);
            planRepository.save(planSaveRequestDto.toEntity());
        }
    }

    @Transactional
    public void updatePlace(PlannerUpdateRequestDto requestDto, Planner planner){
        for(Place place : requestDto.getPlaceList()){
           // 플레이스 추가가 있을 시
            if(place.getId() == null){
                PlaceSaveRequestDto placeSaveRequestDto = new PlaceSaveRequestDto(place, planner);
                placeRepository.save(placeSaveRequestDto.toEntity());
            }
            if(place.getId() != null){
                Place updatePlace = placeRepository.findById(place.getId()).
                        orElseThrow(() -> new IllegalArgumentException("해당 플레이스가 존재하지 않습니다."));

                updatePlace.update(place.getPlace());
            }
        }
    }

    @Transactional
    public void plannerDelete(Long id) {
        Planner planner = plannerRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 플래너가 존재하지 않습니다. id : " + id));
        plannerRepository.delete(planner);
    }

    @Transactional
    public void placeDelete(Planner planner) {
        for(Place place : planner.getPlaceList()){
            Place deletePlace = placeRepository.findById(place.getId()).orElseThrow(() -> new
                    IllegalArgumentException("해당 플랜이 플레이스가 않습니다. id : " + place.getId()));
            placeRepository.delete(deletePlace);
        }
    }

    @Transactional
    public void planDelete(PlannerUpdateRequestDto requestDto, Planner planner){
        for(Plan plan : planner.getPlanList()){
            if(!isIdInPlanner(plan, requestDto)){
                Plan deletePlan = planRepository.findById(plan.getId()).orElseThrow(() -> new
                        IllegalArgumentException("해당 플랜이 존재하지 않습니다. id : " + plan.getId()));
                planRepository.delete(deletePlan);
            }
        }
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
