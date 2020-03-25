package com.kajti.auth.service;

import com.kajti.auth.config.PasswordEncoderConfig;
import com.kajti.auth.domain.User;
import com.kajti.auth.dto.SignUpDto;
import com.kajti.auth.exception.ResourceAlreadyExistsException;
import com.kajti.auth.repository.UserRepository;
import com.kajti.auth.service.uuid.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final PasswordEncoder encoder = PasswordEncoderConfig.passwordEncoder();

    private UserRepository userRepository;
    private UUIDGenerator UUIDGenerator;

    public UserServiceImpl(UserRepository userRepository, UUIDGenerator UUIDGenerator) {
        this.userRepository = userRepository;
        this.UUIDGenerator = UUIDGenerator;
    }

    @Override
    public void create(SignUpDto user) {

        Optional<User> existing = userRepository.findByUsername(user.getUsername());
        existing.ifPresent(it-> {throw new ResourceAlreadyExistsException("user already exists: " + it.getUsername());});

        String hash = encoder.encode(user.getPassword());

        User newUser = User.builder()
                .uuid(UUIDGenerator.generateRandom())
                .username(user.getUsername())
                .password(hash)
                .enabled(user.isEnabled())
                .build();

        userRepository.save(newUser);

        log.info("new user has been created: {}", user.getUsername());
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();

        log.info("user has been deleted");
    }
}