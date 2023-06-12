package com.clicktour.clicktour.service.users;

import com.clicktour.clicktour.common.exception.NotValidException;
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
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserJoinRequestDto register(UserJoinRequestDto userJoinRequestDto){
        userJoinRequestDto.setLoginPassword(passwordEncoder.encode(userJoinRequestDto.getLoginPassword()));
        usersRepository.save(userJoinRequestDto.toEntity());
        return userJoinRequestDto;
    }

    @Transactional
    public String login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        Optional<Users> users = usersRepository.findByLoginId(userLoginRequestDto.getLoginId());
        if(users.isEmpty()){
            return "notFoundId";
        }
        if (!passwordEncoder.matches(userLoginRequestDto.getLoginPassword(), users.get().getLoginPassword())) {
            return "mismatchPassword";
        }

        return jwtTokenProvider.createToken(users.get().getLoginId(), users.get().getRole());
    }

    @Transactional
    public UserInfoResponseDto getUserInfo(String jwtToken) {
        Optional<Users> users;

        try {
            users = usersRepository.findByLoginId(jwtTokenProvider.getUserPK(jwtToken));
        }
        catch (Exception e) {
            return null;
        }

        return users.map(UserInfoResponseDto::new).orElse(null);
    }

    /**
     * 회원가입 유효성 검사
     * @param bindingResult
     */
    public void checkUserValidate(BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            List<String> errorList =
                    bindingResult.getFieldErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.toList());

            throw new NotValidException(errorList);
        }
    }
}

