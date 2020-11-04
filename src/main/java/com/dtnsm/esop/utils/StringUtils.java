package com.dtnsm.esop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private final static String PATTERN_ALPHA_ONLY = "^[a-zA-Z]*$";

    public static boolean isAlphabetOnly(String value) {
        if (value == null) return false;

        Pattern p = Pattern.compile(PATTERN_ALPHA_ONLY);
        Matcher m = p.matcher(value);
        return m.matches();
    }
}
