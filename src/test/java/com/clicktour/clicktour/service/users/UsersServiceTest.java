package com.clicktour.clicktour.service.users;

import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class) // JUnit5 스프링 컨테이너 시작 -> 의존성 주입 가능
@SpringBootTest
class UsersServiceTest {
    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void register() {
        // given
        String loginId = "id";
        String name = "name";
        String nickname = "test";
        String email = "test@test.com";
        String loginPassword = "pw";
        int age = 1;
        String gender = "man";


        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .loginId(loginId)
                .name(name)
                .nickname(nickname)
                .email(email)
                .loginPassword(loginPassword)
                .age(age)
                .gender(gender)
                .build();

        // when
        usersService.register(userJoinRequestDto);

        Users users = usersRepository.findByNickname("test").orElseThrow(() ->
                new IllegalArgumentException("해당 닉네임의 유저가 없습니다.")
        );

        // then
        assertEquals(loginId, users.getLoginId());
        assertEquals(name, users.getName());
        assertEquals(nickname, users.getNickname());
        assertEquals(email, users.getEmail());
        assertEquals(age, users.getAge());
        assertEquals(gender, users.getGender());
        boolean passwordMatches = passwordEncoder.matches(loginPassword, users.getLoginPassword());
        assertTrue(passwordMatches);
    }

    @Test
    void login() {
    }

    @Test
    void getUserInfo() {
    }
}