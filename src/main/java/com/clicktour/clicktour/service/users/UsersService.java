package com.clicktour.clicktour.service.users;

import com.clicktour.clicktour.common.enums.ErrorMessage;
import com.clicktour.clicktour.common.exception.NotValidException;
import com.clicktour.clicktour.common.validators.RegisterValidator;
import com.clicktour.clicktour.config.security.JwtTokenProvider;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.domain.users.dto.UserInfoResponseDto;
import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.domain.users.dto.UserLoginRequestDto;
import com.clicktour.clicktour.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RegisterValidator registerValidator;

    @Transactional
    public void register(UserJoinRequestDto userJoinRequestDto, BindingResult bindingResult) {
        checkUserValidate(userJoinRequestDto, bindingResult);
        userJoinRequestDto.setLoginPassword(passwordEncoder.encode(userJoinRequestDto.getLoginPassword()));
        usersRepository.save(userJoinRequestDto.toEntity());
    }

    /**
     * 로그인
     * @param userLoginRequestDto
     * @return Jwt token
     */
    public String login(UserLoginRequestDto userLoginRequestDto) {
        Users users = checkId(userLoginRequestDto.getLoginId());

        checkPassword(users.getLoginPassword(), userLoginRequestDto.getLoginPassword());

        return jwtTokenProvider.createToken(users.getLoginId(), users.getRole());
    }

    /**
     * 유저 정보 가져오기
     * @param httpServletRequest
     * @return UserInfoResponseDto
     */
    @Transactional
    public UserInfoResponseDto getUserInfo(HttpServletRequest httpServletRequest) {
        String jwtToken = httpServletRequest.getHeader("X-AUTH-TOKEN");

        Users users = checkId(jwtTokenProvider.getUserPK(jwtToken));

        return new UserInfoResponseDto(users);
    }

    /**
     * 회원가입 유효성 검사
     *
     * @param bindingResult
     */
    public void checkUserValidate(UserJoinRequestDto userJoinRequestDto, BindingResult bindingResult) {
        registerValidator.validate(userJoinRequestDto, bindingResult);

        if (bindingResult.hasErrors()) {
            List<String> errorList =
                    bindingResult.getFieldErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.toList());

            throw new NotValidException(errorList);
        }
    }

    /**
     * check Id
     * @param loginId
     * @return Users
     */
    @Transactional
    public Users checkId(String loginId){
        return usersRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NotValidException(List.of(ErrorMessage.NOT_FOUND_ID.getMessage())));
    }

    /**
     * check password
     * @param userPassword
     * @param requestPassword
     */
    public void checkPassword(String userPassword, String requestPassword){
        if (!passwordEncoder.matches(requestPassword, userPassword)) {
            throw new NotValidException(List.of(ErrorMessage.MISMATCH_PASSWORD.getMessage()));
        }
    }
}

