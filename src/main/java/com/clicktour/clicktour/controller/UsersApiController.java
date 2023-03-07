package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.common.message.dto.ResponseDto;
import com.clicktour.clicktour.common.message.enums.ErrorMessage;
import com.clicktour.clicktour.common.message.enums.SuccessMessage;
import com.clicktour.clicktour.config.dto.JwtTokenResponseDto;
import com.clicktour.clicktour.common.message.dto.ExceptionDto;
import com.clicktour.clicktour.domain.users.dto.UserInfoResponseDto;
import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.domain.users.dto.UserLoginRequestDto;
import com.clicktour.clicktour.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UsersApiController {
    private final UsersService usersService;


    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserJoinRequestDto userJoinRequestDto) {

        // 중복된 아이디 또는 닉네임이 있을 때
        if(usersService.register(userJoinRequestDto) == null){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.DUPLICATE_ID_OR_NICKNAME_OR_EMAIL),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseDto(SuccessMessage.SUCCESS_POST), HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        JwtTokenResponseDto jwtTokenResponseDto = new JwtTokenResponseDto(usersService.login(userLoginRequestDto));
        ExceptionDto messageResponseDto;

        // Id가 존재하지 않을 때
        if(jwtTokenResponseDto.getJwtToken().equals("notFoundId")){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_ID), HttpStatus.NOT_FOUND);
        }

        // 비밀번호가 틀릴 때
        if(jwtTokenResponseDto.getJwtToken().equals("mismatchPassword")){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.MISMATCH_PASSWORD), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(jwtTokenResponseDto,HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<?> userInfo(HttpServletRequest httpServletRequest){
        String jwtToken = httpServletRequest.getHeader("X-AUTH-TOKEN");
        UserInfoResponseDto userInfoResponseDto = usersService.getUserInfo(jwtToken);

        if(userInfoResponseDto == null){
            return new ResponseEntity<>(new ExceptionDto(ErrorMessage.NOT_FOUND_ID),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userInfoResponseDto, HttpStatus.OK);
    }
}

