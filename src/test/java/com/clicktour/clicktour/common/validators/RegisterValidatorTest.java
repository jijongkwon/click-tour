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
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegisterValidatorTest {

    @InjectMocks
    private RegisterValidator registerValidator;
    @Mock
    private UsersRepository usersRepository;

    @Test
    public void 이메일_중복_확인_테스트(){
        // Given
        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .email("test@example.com")
                .build();


        Mockito.when(usersRepository.existsByEmail("test@example.com")).thenReturn(true);

        // When
        Errors errors = new BeanPropertyBindingResult(userJoinRequestDto, "userJoinRequestDto");
        registerValidator.validate(userJoinRequestDto, errors);

        // Then
        assertTrue(errors.hasErrors());
        assertEquals("이메일 중복", errors.getFieldError("email").getDefaultMessage());
    }

    @Test
    public void 아이디_중복_확인_테스트(){
        // Given
        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .loginId("test")
                .build();


        Mockito.when(usersRepository.existsByLoginId("test")).thenReturn(true);

        // When
        Errors errors = new BeanPropertyBindingResult(userJoinRequestDto, "userJoinRequestDto");
        registerValidator.validate(userJoinRequestDto, errors);

        // Then
        assertTrue(errors.hasErrors());
        assertEquals("아이디 중복", errors.getFieldError("loginId").getDefaultMessage());
    }

    @Test
    public void 닉네임_중복_확인_테스트(){
        // Given
        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .nickname("test")
                .build();


        Mockito.when(usersRepository.existsByNickname("test")).thenReturn(true);

        // When
        Errors errors = new BeanPropertyBindingResult(userJoinRequestDto, "userJoinRequestDto");
        registerValidator.validate(userJoinRequestDto, errors);

        // Then
        assertTrue(errors.hasErrors());
        assertEquals("닉네임 중복", errors.getFieldError("nickname").getDefaultMessage());
    }
}