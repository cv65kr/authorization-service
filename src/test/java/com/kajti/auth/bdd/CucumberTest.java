package com.kajti.auth.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        stepNotifications = true,
        features = "classpath:features",
        plugin = {"pretty"},
        glue = {"com.kajti.auth.bdd.steps"},
        strict = true
)
public class CucumberTest extends SpringIntegrationTest {
}