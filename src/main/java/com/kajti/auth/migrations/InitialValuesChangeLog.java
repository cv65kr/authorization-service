package com.kajti.auth.migrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.kajti.auth.config.PasswordEncoderConfig;
import com.kajti.auth.domain.AuthClientDetails;
import com.kajti.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@ChangeLog
public class InitialValuesChangeLog {

    @Autowired
    private PasswordEncoder passwordEncoder = PasswordEncoderConfig.passwordEncoder();

    @ChangeSet(order = "001", id = "insertBrowserClientDetails", author = "auth-service")
    public void insertBrowserClientDetails(MongoTemplate mongoTemplate) {
        AuthClientDetails clientDetails = AuthClientDetails.builder()
                .uuid(UUID.randomUUID())
                .clientId("browser")
                .clientSecret("")
                .scopes("ui")
                .grantTypes("refresh_token,password")
                .build();

        mongoTemplate.save(clientDetails);
    }

    @ChangeSet(order = "002", id = "insertServiceClientDetails", author = "auth-service")
    public void insertServiceClientDetails(MongoTemplate mongoTemplate) {
        AuthClientDetails clientDetails = AuthClientDetails.builder()
                .uuid(UUID.randomUUID())
                .clientId("service")
                .clientSecret(passwordEncoder.encode("service-password"))
                .scopes("server")
                .grantTypes("client_credentials,refresh_token")
                .build();

        mongoTemplate.save(clientDetails);
    }

    @ChangeSet(order = "003", id = "insertUser", author = "auth-service")
    public void insertUser(MongoTemplate mongoTemplate) {
        User user = User.builder()
                .uuid(UUID.randomUUID())
                .username("test")
                .password(passwordEncoder.encode("test-password"))
                .enabled(true)
                .build();

        mongoTemplate.save(user);
    }
}
