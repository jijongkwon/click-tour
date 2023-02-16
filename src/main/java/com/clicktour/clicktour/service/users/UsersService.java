package com.clicktour.clicktour.service.users;

import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserJoinRequestDto register(UserJoinRequestDto userJoinRequestDto){
        userJoinRequestDto.setLoginPassword(passwordEncoder.encode(userJoinRequestDto.getLoginPassword()));
        usersRepository.save(userJoinRequestDto.toEntity());
        return userJoinRequestDto;
    }
}

