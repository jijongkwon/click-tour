package com.clicktour.clicktour.domain.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자"),
    CREW_HEAD("ROLE_CREW_HEAD", "크루 리더");

    private final String key;
    private final String title;
}
