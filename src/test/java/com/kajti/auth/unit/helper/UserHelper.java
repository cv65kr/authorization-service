package com.kajti.auth.unit.helper;

import com.kajti.auth.domain.User;

import java.util.UUID;

public class UserHelper {
    public static User getUserInstance() {
        return User.builder()
                .uuid(UUID.fromString("6bbdc992-3078-46f4-b7ca-0424f0862a4c"))
                .username("test")
                .password("test")
                .enabled(true)
                .build();
    }
}
