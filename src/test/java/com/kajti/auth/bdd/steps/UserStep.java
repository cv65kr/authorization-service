package com.kajti.auth.bdd.steps;

import com.kajti.auth.dto.SignUpDto;
import com.kajti.auth.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class UserStep {

    public static String PASSWORD = "password";

    @Autowired
    private UserService userService;

    @Before
    public void before(Scenario scenario) {
        userService.deleteAll();
    }

    @Given("There is user registered with {string} username")
    public void thereIsUserRegisteredWithUsername(String username) {

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername(username);
        signUpDto.setPassword(PASSWORD);
        signUpDto.setEnabled(true);

        userService.create(signUpDto);
    }
}
