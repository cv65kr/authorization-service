package com.kajti.auth.unit.service;

import com.kajti.auth.domain.AuthClientDetails;
import com.kajti.auth.exception.ResourceAlreadyExistsException;
import com.kajti.auth.exception.ResourceNotExistsException;
import com.kajti.auth.repository.AuthClientRepository;
import com.kajti.auth.service.AuthClientDetailsService;
import com.kajti.auth.service.AuthClientDetailsServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AuthClientDetailsServiceTests {

    @Mock
    AuthClientRepository authClientRepository;

    AuthClientDetailsService authClientDetailsService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        authClientDetailsService = new AuthClientDetailsServiceImpl(authClientRepository);
    }

    @Test
    public void testSuccessfulLoadClient() {
        when(authClientRepository.findByClientId("client")).thenReturn(Optional.of(getAuthClientDetailsInstance()));

        ClientDetails clientDetails = authClientDetailsService.loadClientByClientId("client");

        assertEquals("client", clientDetails.getClientId());
        assertEquals("secret", clientDetails.getClientSecret());
        assertEquals(new HashSet<>(Arrays.asList("password")), clientDetails.getAuthorizedGrantTypes());
        assertEquals(new HashSet<>(Arrays.asList("mobile", "web")), clientDetails.getScope());
        assertEquals(new HashSet<>(Arrays.asList("resource")), clientDetails.getResourceIds());
        assertEquals(new HashSet<>(Arrays.asList("uri")), clientDetails.getRegisteredRedirectUri());
        assertEquals(1, (long) clientDetails.getAccessTokenValiditySeconds());
        assertNull( clientDetails.getAdditionalInformation());
        assertEquals(2, (long) clientDetails.getRefreshTokenValiditySeconds());
    }

    @Test
    public void testThrowExceptionWhileClientNotExists() {
        exceptionRule.expect(ResourceNotExistsException.class);

        when(authClientRepository.findByClientId("client")).thenReturn(Optional.empty());

        authClientDetailsService.loadClientByClientId("client");
    }

    private AuthClientDetails getAuthClientDetailsInstance() {
        return AuthClientDetails.builder()
                .uuid(UUID.fromString("6bbdc992-3078-46f4-b7ca-0424f0862a4c"))
                .clientId("client")
                .clientSecret("secret")
                .grantTypes("password")
                .scopes("web,mobile")
                .resources("resource")
                .redirectUris("uri")
                .accessTokenValidity(1)
                .additionalInformation(null)
                .refreshTokenValidity(2)
                .build();
    }
}
