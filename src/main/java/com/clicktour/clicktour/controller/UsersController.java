package com.clicktour.clicktour.controller;

import com.clicktour.clicktour.config.security.JwtTokenProvider;
import com.clicktour.clicktour.domain.users.Role;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.repository.UsersRepository;
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
public class UsersController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;


    // 회원가입
    @PostMapping("/register")
    public Long register(@RequestBody Map<String, String> user) {
        return usersRepository.save(Users.builder()
                .email(user.get("email"))
                .login_password(passwordEncoder.encode(user.get("login_password")))
                .nick_name(user.get("nick_name"))
                .intro(user.get("intro"))
                .Gender(user.get("gender"))
                .picture(user.get("picture"))
                .age(Integer.parseInt(user.get("age")))
                .login_id(user.get("login_id"))
                .address(user.get("address"))
                .name(user.get("name"))
                .role(Role.USER)
                .build()).getId();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        Users users = usersRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));
        System.out.println(user.get("login_password"));
        System.out.println(users.getLogin_password());
        if (!passwordEncoder.matches(user.get("login_password"), users.getLogin_password())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }

        return jwtTokenProvider.createToken(users.getEmail(), users.getRole());
    }
}
