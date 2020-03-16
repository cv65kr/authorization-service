package com.kajti.auth.service;

import com.kajti.auth.repository.AuthClientRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthClientDetailsServiceImpl implements AuthClientDetailsService {
    private final AuthClientRepository authClientRepository;

    public AuthClientDetailsServiceImpl(AuthClientRepository authClientRepository) {
        this.authClientRepository = authClientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        return authClientRepository.findByClientId(clientId).orElseThrow(IllegalArgumentException::new);
    }
}