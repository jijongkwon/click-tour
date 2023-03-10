package com.clicktour.clicktour.service.users;

import com.clicktour.clicktour.config.security.JwtTokenProvider;
import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.domain.users.dto.UserInfoResponseDto;
import com.clicktour.clicktour.domain.users.dto.UserJoinRequestDto;
import com.clicktour.clicktour.domain.users.dto.UserLoginRequestDto;
import com.clicktour.clicktour.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserJoinRequestDto register(UserJoinRequestDto userJoinRequestDto){
        Optional<Users> usersId = usersRepository.findByLoginId(userJoinRequestDto.getLoginId());
        Optional<Users> usersNickname = usersRepository.findByNickname(userJoinRequestDto.getNickname());
        Optional<Users> usersEmail = usersRepository.findByEmail(userJoinRequestDto.getEmail());

        // id, nickname, email 중복
        if(usersId.isPresent() || usersNickname.isPresent() || usersEmail.isPresent()){
            return null;
        }

        // 회원가입
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
}

