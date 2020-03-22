package com.kajti.auth.bdd.matcher;

import net.javacrumbs.jsonunit.core.ParametrizedMatcher;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimestampMatcher extends BaseMatcher<Object> implements ParametrizedMatcher {
    private String param;

    public boolean matches(Object item) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.param);

        try {
            LocalDateTime.parse((String) item, formatter);

            return true;
        } catch (DateTimeParseException exp) {}

        return false;
    }

    public void describeTo(Description description) {
        description.appendValue(param);
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("Invalid timestamp: ").appendValue(param);
    }

    public void setParameter(String parameter) {
        this.param = parameter;
    }
}