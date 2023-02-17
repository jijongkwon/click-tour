package com.clicktour.clicktour.service.users;

import com.clicktour.clicktour.config.security.JwtTokenProvider;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.domain.users.dto.UserLoginRequestDto;
import com.clicktour.clicktour.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserJoinRequestDto register(UserJoinRequestDto userJoinRequestDto){
        Optional<Users> usersId = usersRepository.findByLoginId(userJoinRequestDto.getLoginId());
        Optional<Users> usersNickname = usersRepository.findByNickname(userJoinRequestDto.getNickname());

        // id, nickname 중복
        if(usersId.isPresent() || usersNickname.isPresent()){
            return null;
        }

        // 회원가입
        userJoinRequestDto.setLoginPassword(passwordEncoder.encode(userJoinRequestDto.getLoginPassword()));
        usersRepository.save(userJoinRequestDto.toEntity());
        return userJoinRequestDto;
    }

    @Transactional
    public String login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        Users users = usersRepository.findByLoginId(userLoginRequestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 아이디입니다."));
        if (!passwordEncoder.matches(userLoginRequestDto.getLoginPassword(), users.getLoginPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }

        return jwtTokenProvider.createToken(users.getLoginId(), users.getRole());
    }
}

