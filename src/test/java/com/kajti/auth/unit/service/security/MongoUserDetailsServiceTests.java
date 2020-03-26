package com.kajti.auth.unit.service.security;

import com.kajti.auth.domain.User;
import com.kajti.auth.helper.UserHelper;
import com.kajti.auth.repository.UserRepository;
import com.kajti.auth.service.security.MongoUserDetailsService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MongoUserDetailsServiceTests {

    @Mock
    UserRepository userRepository;

    MongoUserDetailsService mongoUserDetailsService;

    User userInstance = UserHelper.getUserInstance();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        mongoUserDetailsService = new MongoUserDetailsService(userRepository);
    }

    @Test
    public void testSuccessfulLoadUserByUsername() {
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(userInstance));

        UserDetails userDetails = mongoUserDetailsService.loadUserByUsername("test");

        assertEquals("test", userDetails.getUsername());
        assertEquals("test", userDetails.getPassword());
        assertNull(userDetails.getAuthorities());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void testThrowExceptionWhileLoadUserByUsername() {
        exceptionRule.expect(UsernameNotFoundException.class);
        exceptionRule.expectMessage("test");

        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());

        mongoUserDetailsService.loadUserByUsername("test");
    }
}
