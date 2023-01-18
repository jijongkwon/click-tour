package com.clicktour.clicktour.service.planner;

import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.dto.PlannerResponseDto;
import com.clicktour.clicktour.domain.planner.dto.PlannerSaveRequestDto;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.repository.PlannerRepository;
import com.clicktour.clicktour.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PlannerService {
    private final PlannerRepository plannerRepository;

    @Transactional
    public Long save(PlannerSaveRequestDto requestDto){
        return plannerRepository.save(requestDto.toEntity()).getId();
    }
}
