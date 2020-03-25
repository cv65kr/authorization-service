package com.kajti.auth.service.uuid;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGeneratorImpl implements UUIDGenerator {
    @Override
    public UUID generateRandom() {
        return UUID.randomUUID();
    }
}
