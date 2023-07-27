package com.clicktour.clicktour.service.users;


import com.clicktour.clicktour.domain.users.Users;
import com.clicktour.clicktour.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UnitUserServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

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
}
