package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.config.security.JwtTokenProvider;
import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.domain.users.dto.UserLoginRequestDto;
import com.clicktour.clicktour.repository.UsersRepository;
import com.clicktour.clicktour.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UsersService usersService;


    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserJoinRequestDto userJoinRequestDto) {
        if(usersService.register(userJoinRequestDto) == null){
            return new ResponseEntity<>("아이디 또는 닉네임이 중복입니다.",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userJoinRequestDto, HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        return usersService.login(userLoginRequestDto);
    }
}

