package com.clicktour.clicktour.domain.users.dto;

import com.clicktour.clicktour.domain.users.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoResponseDto {

    private Long id;
    private String nickname;

    public UserInfoResponseDto(Users users){
        this.id = users.getId();
        this.nickname = users.getNickname();
    }
}
