package com.kajti.auth.service;

import com.kajti.auth.dto.SignUpDto;

public interface UserService {

    void create(SignUpDto user);
}