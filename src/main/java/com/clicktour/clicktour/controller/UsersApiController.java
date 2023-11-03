package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.common.dto.ResponseDto;
import com.clicktour.clicktour.common.enums.SuccessMessage;
import com.clicktour.clicktour.config.dto.JwtTokenResponseDto;
import com.clicktour.clicktour.domain.users.dto.UserInfoResponseDto;
import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.domain.users.dto.UserLoginRequestDto;
import com.clicktour.clicktour.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UsersApiController {
    private final UsersService usersService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserJoinRequestDto userJoinRequestDto, BindingResult bindingResult) {
        usersService.register(userJoinRequestDto, bindingResult);
        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_REGISTER), HttpStatus.OK);
    }

    /**
     * 로그인
     *
     * @param userLoginRequestDto
     * @return responseEntity
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        JwtTokenResponseDto jwtTokenResponseDto = new JwtTokenResponseDto(usersService.login(userLoginRequestDto));
        return new ResponseEntity<>(jwtTokenResponseDto, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<?> userInfo(HttpServletRequest httpServletRequest) {
        UserInfoResponseDto userInfoResponseDto = usersService.getUserInfo(httpServletRequest);
        return new ResponseEntity<>(userInfoResponseDto, HttpStatus.OK);
    }
}

