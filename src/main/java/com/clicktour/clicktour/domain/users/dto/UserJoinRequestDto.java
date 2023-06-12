package com.clicktour.clicktour.domain.users.dto;

import com.clicktour.clicktour.domain.users.Role;
import com.clicktour.clicktour.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequestDto {
    private String loginId;
    private String loginPassword;
    private String email;
    private String name;
    private String nickname;
    private int age;
    private String gender;
    private String address;
    private String intro;
    private String picture;
    private Role role;

    @Builder
    public UserJoinRequestDto(String loginId, String loginPassword, String email, String name, String nickname,
                              int age, String gender, String address, String intro, String picture) {
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.intro = intro;
        this.picture = picture;
    }

    public Users toEntity() {
        return Users.builder().
                loginId(loginId).
                loginPassword(loginPassword).
                email(email).
                name(name).
                nickname(nickname).
                age(age).
                gender(gender).
                address(address).
                intro(intro).
                picture(picture).
                role(Role.USER).
                build();
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}

