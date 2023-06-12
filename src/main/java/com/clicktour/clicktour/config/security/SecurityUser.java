package com.clicktour.clicktour.config.security;

import com.clicktour.clicktour.domain.users.Users;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Getter
public class SecurityUser extends User {
    private final Users users;

    public SecurityUser(Users users) {
        super(users.getLoginId(), users.getLoginPassword(),
                AuthorityUtils.createAuthorityList(users.getRole().toString()));
        this.users = users;
    }
}
