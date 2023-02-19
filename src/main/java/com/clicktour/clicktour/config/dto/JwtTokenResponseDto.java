package com.clicktour.clicktour.config.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtTokenResponseDto {

    private String JwtToken;

    public JwtTokenResponseDto(String jwtToken){
        this.JwtToken = jwtToken;
    }
}
