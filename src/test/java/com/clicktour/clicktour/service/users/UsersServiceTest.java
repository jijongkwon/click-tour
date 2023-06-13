package com.clicktour.clicktour.service.users;

import com.clicktour.clicktour.common.validators.RegisterValidator;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UsersServiceTest {
    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RegisterValidator registerValidator;

    @Test
    void register() {
        // given
        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .loginId("id")
                .name("name")
                .nickname("test")
                .email("test@email.com")
                .loginPassword("pw")
                .age(3)
                .gender("man")
                .build();

        // when
        BindingResult bindingResult = new BeanPropertyBindingResult(userJoinRequestDto, "userJoinRequestDto");
        usersService.register(userJoinRequestDto, bindingResult);

        Users users = usersRepository.findByNickname(userJoinRequestDto.getNickname()).orElseThrow(() ->
                new IllegalArgumentException("닉네임이 일치하지 않음"));

        // then
        assertEquals("id", users.getLoginId());
        assertEquals("name", users.getName());
        assertEquals("test", users.getNickname());
        assertEquals("test@email.com", users.getEmail());
        assertEquals(3, users.getAge());
        assertEquals("man", users.getGender());
        boolean passwordMatches = passwordEncoder.matches("pw", users.getLoginPassword());
        assertTrue(passwordMatches);
    }

    @Test
    void login() {
    }

    @Test
    void getUserInfo() {
    }
}