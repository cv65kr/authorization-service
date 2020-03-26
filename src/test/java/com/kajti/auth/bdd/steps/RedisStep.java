package com.kajti.auth.bdd.steps;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;

public class RedisStep {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Given("There is not entries in Redis")
    public void flushAll() {
        redisConnectionFactory.getConnection().flushAll();
    }
}
