package com.kajti.auth.config;

import com.github.mongobee.Mongobee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.MongoTemplate;

@DependsOn("mongoTemplate")
@Configuration
public class MongoBeeConfig {

    @Autowired
    private MongoProperties mongoProperties;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public Mongobee mongobee() {
        Mongobee runner = new Mongobee(mongoProperties.getUri());
        runner.setChangeLogsScanPackage(getMigrationsNamespace());
        runner.setMongoTemplate(mongoTemplate);

        return runner;
    }

    private String getMigrationsNamespace() {
        Package pack = this.getClass().getPackage();

        return pack.getName().replace("config", "migrations");
    }
}
