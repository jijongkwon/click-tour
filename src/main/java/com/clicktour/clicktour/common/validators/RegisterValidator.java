package com.clicktour.clicktour.common.validators;

import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component // 빈 등록
@RequiredArgsConstructor
public class RegisterValidator implements Validator {

    private final UsersRepository usersRepository;

    /**
     * supports() 메서드는 Validator 인터페이스의 메서드로서, 주어진 클래스가 Validator가 유효성 검사를 수행할 수 있는 대상인지 여부를 결정
     * @param clazz the {@link Class} that this {@link Validator} is
     * being asked if it can {@link #validate(Object, Errors) validate}
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return UserJoinRequestDto.class.equals(clazz);
    }

    /**
     * 중복 체크
     * @param target the object that is to be validated
     * @param errors contextual state about the validation process
     */
    @Override
    public void validate(Object target, Errors errors) {
        UserJoinRequestDto userJoinRequestDto = (UserJoinRequestDto) target;

        // 이메일 중복 체크
        if (usersRepository.existsByEmail(userJoinRequestDto.getEmail())) {
            errors.rejectValue("email", "400", "이메일 중복");
        }

        // 아이디 중복 체크
        if (usersRepository.existsByLoginId(userJoinRequestDto.getLoginId())) {
            errors.rejectValue("loginId", "400", "아이디 중복");
        }

        // 닉네임 중복 체크
        if (usersRepository.existsByNickname(userJoinRequestDto.getNickname())) {
            errors.rejectValue("nickname", "400", "닉네임 중복");
        }
    }
}
