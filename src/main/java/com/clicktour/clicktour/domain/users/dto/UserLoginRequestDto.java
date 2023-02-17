package com.clicktour.clicktour.domain.users.dto;

import com.clicktour.clicktour.domain.users.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {

    private String loginId;
    private String loginPassword;

    public UserLoginRequestDto(Users users){
        this.loginId = users.getLoginId();
        this.loginPassword = users.getLoginPassword();
    }
}
