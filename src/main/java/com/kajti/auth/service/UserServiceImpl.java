package com.kajti.auth.service;

import com.kajti.auth.config.PasswordEncoderConfig;
import com.kajti.auth.domain.User;
import com.kajti.auth.dto.SignUpDto;
import com.kajti.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final PasswordEncoder encoder = PasswordEncoderConfig.passwordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(SignUpDto user) {

        Optional<User> existing = userRepository.findByUsername(user.getUsername());
        existing.ifPresent(it-> {throw new IllegalArgumentException("user already exists: " + it.getUsername());});

        String hash = encoder.encode(user.getPassword());

        User newUser = User.builder()
                .uuid(UUID.randomUUID())
                .username(user.getUsername())
                .password(hash)
                .enabled(true)
                .build();

        userRepository.save(newUser);

        log.info("new user has been created: {}", user.getUsername());
    }
}