package com.clicktour.clicktour.common.validators;

import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class RegisterValidatorTest {

    @InjectMocks
    private RegisterValidator registerValidator;
    @Mock
    private UsersRepository usersRepository;

    @Test
    public void 이메일_중복_확인_테스트() {
        // Given
        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .email("test@example.com")
                .build();


        Mockito.when(usersRepository.existsByEmail("test@example.com")).thenReturn(true);

        // When
        BindingResult bindingResult = new BeanPropertyBindingResult(userJoinRequestDto, "userJoinRequestDto");
        registerValidator.validate(userJoinRequestDto, bindingResult);

        // Then
        assertTrue(bindingResult.hasErrors());
        assertEquals("이메일 중복", bindingResult.getFieldError("email").getDefaultMessage());
    }

    @Test
    public void 아이디_중복_확인_테스트() {
        // Given
        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .loginId("test")
                .build();


        Mockito.when(usersRepository.existsByLoginId("test")).thenReturn(true);

        // When
        BindingResult bindingResult = new BeanPropertyBindingResult(userJoinRequestDto, "userJoinRequestDto");
        registerValidator.validate(userJoinRequestDto, bindingResult);

        // Then
        assertTrue(bindingResult.hasErrors());
        assertEquals("아이디 중복", bindingResult.getFieldError("loginId").getDefaultMessage());
    }

    @Test
    public void 닉네임_중복_확인_테스트() {
        // Given
        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .nickname("test")
                .build();


        Mockito.when(usersRepository.existsByNickname("test")).thenReturn(true);

        // When
        BindingResult bindingResult = new BeanPropertyBindingResult(userJoinRequestDto, "userJoinRequestDto");
        registerValidator.validate(userJoinRequestDto, bindingResult);

        // Then
        assertTrue(bindingResult.hasErrors());
        assertEquals("닉네임 중복", bindingResult.getFieldError("nickname").getDefaultMessage());
    }
}