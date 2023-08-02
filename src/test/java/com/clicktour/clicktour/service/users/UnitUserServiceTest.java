package com.clicktour.clicktour.service.users;


import com.clicktour.clicktour.common.exception.NotValidException;
import com.clicktour.clicktour.config.security.JwtTokenProvider;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.domain.users.dto.UserInfoResponseDto;
import com.clicktour.clicktour.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UnitUserServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    void 아이디_화인(){
        // given
        String loginId = "test";
        Users mockUsers = new Users();

        when(usersRepository.findByLoginId(loginId)).thenReturn(Optional.of(mockUsers));

        // when
        Users result = usersService.checkId(loginId);

        // then
        assertNotNull(result);
        assertEquals(mockUsers,result);
    }

    @Test
    void 아이디_확인_예외처리() {
        // given
        String loginId = "nonExistentUser";

        // when
        when(usersRepository.findByLoginId(loginId)).thenReturn(Optional.empty());

        // then
        assertThrows(NotValidException.class, () -> usersService.checkId(loginId));
    }

    @Test
    void 비밀번호_확인(){
        // given
        String userPassword = "test";
        String requestPassword = "test";

        // when
        when(passwordEncoder.matches(requestPassword, userPassword)).thenReturn(true);

        // then
        assertDoesNotThrow(()-> usersService.checkPassword(userPassword,requestPassword));
    }

    @Test
    void 비밀번호_확인_예외처리(){
        // given
        String userPassword = "test";
        String requestPassword = "diff";

        // when

        // then
        assertThrows(NotValidException.class, () -> usersService.checkPassword(userPassword,requestPassword));
    }

    @Test
    public void 유저정보확인() {
        // given
        String jwtToken = "valid_jwt_token";
        Users user = Users.builder()
                .loginId("testUser")
                .nickname("test")
                .build();

        when(httpServletRequest.getHeader("X-AUTH-TOKEN")).thenReturn(jwtToken);
        when(jwtTokenProvider.getUserPK(jwtToken)).thenReturn("testUser");
        when(usersRepository.findByLoginId("testUser")).thenReturn(Optional.of(user));

        // when
        UserInfoResponseDto userInfoResponseDto = usersService.getUserInfo(httpServletRequest);

        // then
        assertNotNull(userInfoResponseDto);
        assertEquals("test", userInfoResponseDto.getNickname());
    }
}
