package com.kajti.auth.helper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SplitString {
    public static Set<String> execute(String value) {
        if (value == null) {
            return null;
        }

        return new HashSet<>(Arrays.asList(value.split(",")));
    }
}