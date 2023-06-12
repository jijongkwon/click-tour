package com.clicktour.clicktour.service.users;

import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {
    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    String loginId = "id";
    String name = "name";
    String nickname = "test";
    String email = "test@test.com";
    String loginPassword = "pw";
    int age = 1;
    String gender = "man";

    @Test
    void register() {
        // given
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
                new IllegalArgumentException("닉네임이 일치하지 않음"));

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