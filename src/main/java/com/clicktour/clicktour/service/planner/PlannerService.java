package com.clicktour.clicktour.service.planner;

import com.clicktour.clicktour.domain.planner.Place;
import com.clicktour.clicktour.domain.planner.Plan;
import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.dto.*;
import com.clicktour.clicktour.domain.users.Role;
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

import java.text.SimpleDateFormat;
import java.util.*;
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

        /* 플래너 수정 */
        Planner planner = plannerRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 플래너가 존재하지 않습니다. id : " + id));
        planner.update(requestDto.getTitle(), requestDto.getStart_date(), requestDto.getEnd_date(),
                requestDto.getIntro(), requestDto.getConcept(), requestDto.getVisibility());

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
            if(plan.getId() == null){
                PlanSaveRequestDto planSaveRequestDto = new PlanSaveRequestDto(plan, planner);
                planRepository.save(planSaveRequestDto.toEntity());
            }
            if(plan.getId() != null){
                Plan updatePlan = planRepository.findById(plan.getId()).
                        orElseThrow(() -> new IllegalArgumentException("해당 플랜이 존재하지 않습니다."));

                updatePlan.update(plan.getName(),plan.getMemo(),plan.getDate(),plan.getX(),plan.getY());
            }
        }
    }

    @Transactional
    public void updatePlace(PlannerUpdateRequestDto requestDto, Planner planner){
        for(Place place : requestDto.getPlaceList()){
            PlaceSaveRequestDto placeSaveRequestDto = new PlaceSaveRequestDto(place, planner);
            placeRepository.save(placeSaveRequestDto.toEntity());
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

    @Transactional
    public PlannerDetailResponseDto recommendPlanner(PlannerRecommendRequestDto recommendRequestDto){
        List<Planner> allPlanners =  plannerRepository.findAllDesc();
        // 추천 목록 추가
        List<Planner> recommendPlannerList = addRecommendPlannerList(allPlanners,recommendRequestDto);

        // 랜덤 번호 생성
        int randomIndex = creatRandomNumber(recommendPlannerList);

        return new PlannerDetailResponseDto(recommendPlannerList.get(randomIndex));
    }

    public List<Planner> addRecommendPlannerList(List<Planner> allPlanners, PlannerRecommendRequestDto recommendRequestDto){
        List<Planner> recommendPlannerList = new ArrayList<>();

        for(Planner planner : allPlanners) {
            if (isMeetRequirementForRecommendPlanner(planner, recommendRequestDto)) {
                for (Place requestPlace : recommendRequestDto.getPlaceList()) {
                    for(Place responsePlace : planner.getPlaceList()){
                        if(requestPlace.getPlace().equals(responsePlace.getPlace())){
                            recommendPlannerList.add(planner);
                            break;
                        }
                    }
                }
            }
        }
        return recommendPlannerList;
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

    public boolean isEqualStartDate(Planner planner, PlannerRecommendRequestDto recommendRequestDto){
        return planner.getStart_date().toString().equals(transportDateFormat(recommendRequestDto.getStartDate()));
    }

    public boolean isEqualEndDate(Planner planner, PlannerRecommendRequestDto recommendRequestDto){
        return planner.getEnd_date().toString().equals(transportDateFormat(recommendRequestDto.getEndDate()));
    }

    public boolean isEqualConcept(Planner planner, PlannerRecommendRequestDto recommendRequestDto){
        return planner.getConcept().equals(recommendRequestDto.getConcept());
    }

    public boolean isAdmin(Planner planner){
        return planner.getUsers().getRole().equals(Role.ADMIN);
    }

    public boolean isMeetRequirementForRecommendPlanner(Planner planner, PlannerRecommendRequestDto recommendRequestDto){
        if(!isEqualStartDate(planner,recommendRequestDto)){
            return false;
        }

        if(!isEqualEndDate(planner,recommendRequestDto)){
            return false;
        }

        if(!isEqualConcept(planner,recommendRequestDto)){
            return false;
        }

//        if(!isAdmin(planner)){
//            return false;
//        }

        return true;
    }

    public int creatRandomNumber(List<Planner> recommendPlannerList){
        Random random = new Random();
        return random.nextInt(recommendPlannerList.size()) + 1;
    }

    public String transportDateFormat(Date date){
        SimpleDateFormat transportDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
        return transportDate.format(date);
    }
}
