package com.clicktour.clicktour.domain.users.dto;

import com.clicktour.clicktour.domain.users.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoResponseDto {

    private String nickname;

    public UserInfoResponseDto(Users users){
        this.nickname = users.getNickname();
    }
}
