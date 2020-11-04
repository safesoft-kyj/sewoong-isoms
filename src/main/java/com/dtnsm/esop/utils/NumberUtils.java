package com.dtnsm.esop.utils;



import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class NumberUtils {
    private final static String PATTERN_NUMBER_ONLY = "^[0-9]*$";

    /**
     * 숫자형인지 판단한다. 소숫점도 숫자형으로 판단한다.
     *
     * @param value
     * @return 숫자이면 true, 숫자가 아니면 false
     */
    public static boolean isNumber(String value) {
        if (value == null) return false;

        Pattern p = Pattern.compile("([\\p{Digit}]+)(([.]?)([\\p{Digit}]+))?");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * 숫자만 소수점 허용 안함.
     * @param value
     * @return
     */
    public static boolean isNumberOnly(String value) {
        if (value == null) return false;

        Pattern p = Pattern.compile(PATTERN_NUMBER_ONLY);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * 입력한 문자열이 해당 정규식 패턴에 일치하는지 여부를 판단한다.
     * @param value
     * @param pattern
     * @return
     */
    public static boolean isNumber(String value, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        boolean isMatch = m.matches();
        return isMatch;
    }

}