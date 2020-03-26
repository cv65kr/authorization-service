package com.kajti.auth.unit.service;

import com.kajti.auth.domain.User;
import com.kajti.auth.dto.SignUpDto;
import com.kajti.auth.exception.ResourceAlreadyExistsException;
import com.kajti.auth.helper.UserHelper;
import com.kajti.auth.repository.UserRepository;
import com.kajti.auth.service.UserService;
import com.kajti.auth.service.UserServiceImpl;
import com.kajti.auth.service.uuid.UUIDGenerator;
import com.kajti.auth.unit.LogSpy;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Mock
    UserRepository userRepository;

    @Mock
    UUIDGenerator UUIDGenerator;

    UserService userService;

    User userInstance = UserHelper.getUserInstance();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Rule
    public LogSpy logSpy = new LogSpy();

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository, UUIDGenerator);
    }

    @Test
    public void testSuccessfulCreateUser() throws Exception {
        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());
        when(userRepository.save(userInstance)).thenReturn(userInstance);
        when(UUIDGenerator.generateRandom()).thenReturn(UUID.fromString("6bbdc992-3078-46f4-b7ca-0424f0862a4c"));

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("test");
        signUpDto.setPassword("password");
        signUpDto.setEnabled(true);

        userService.create(signUpDto);

        assertEquals(true, logSpy.findEvent("new user has been created: test"));
    }

    @Test
    public void testThrowExceptionWhileCreatingUserBecauseUserAlreadyExists() {
        exceptionRule.expect(ResourceAlreadyExistsException.class);
        exceptionRule.expectMessage("user already exists: test");

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(userInstance));

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("test");
        signUpDto.setPassword("password");
        signUpDto.setEnabled(true);

        userService.create(signUpDto);
    }

    @Test
    public void testSuccessfulDeleteAllUsers() throws Exception {
        userService.deleteAll();

        assertEquals(true, logSpy.findEvent("user has been deleted"));
    }
}
