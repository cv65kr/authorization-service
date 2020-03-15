package com.kajti.auth.repository;

import com.kajti.auth.domain.AuthClientDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthClientRepository extends MongoRepository<AuthClientDetails, UUID> {
    Optional<AuthClientDetails> findByClientId(String clientId);
}
