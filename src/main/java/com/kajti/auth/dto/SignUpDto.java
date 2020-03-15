package com.kajti.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SignUpDto {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
