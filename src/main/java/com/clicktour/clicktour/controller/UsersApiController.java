package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.config.security.JwtTokenProvider;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.repository.UsersRepository;
import com.clicktour.clicktour.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UsersApiController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;
    private final UsersService usersService;


    // 회원가입
    @PostMapping("/register")
    public UserJoinRequestDto register(@RequestBody UserJoinRequestDto userJoinRequestDto) {
        return usersService.register(userJoinRequestDto);
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        Users users = usersRepository.findByLoginId(user.get("loginId"))
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 아이디입니다."));
        if (!passwordEncoder.matches(user.get("loginPassword"), users.getLoginPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }

        return jwtTokenProvider.createToken(users.getLoginId(), users.getRole());
    }
}

