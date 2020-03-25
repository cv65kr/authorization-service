package com.kajti.auth.bdd.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import java.util.UUID;

public class UuidMatcher extends BaseMatcher<Object> {

    public boolean matches(Object item) {

        try{
            UUID.fromString((String) item);
            return true;
        } catch (IllegalArgumentException exception){
            //handle the case where string is not valid UUID
        }

        return false;
    }

    public void describeTo(Description description) {
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("Invalid uuid");
    }
}