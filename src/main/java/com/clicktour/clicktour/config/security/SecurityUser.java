package com.clicktour.clicktour.config.security;

import com.clicktour.clicktour.domain.users.Users;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Getter
public class SecurityUser extends User {
    private Users users;

    public SecurityUser(Users users){
        super(users.getLogin_id(), users.getLogin_password(),
                AuthorityUtils.createAuthorityList(users.getRole().toString()));
        this.users = users;
    }
}
