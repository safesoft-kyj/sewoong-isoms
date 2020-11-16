package com.cauh.esop.utils;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.util.ObjectUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
public class DateUtils {
    private final static String YYYY_MM_DD = "yyyy-MM-dd";
    private final static String DATE_PATTERN_ONLY_NUMBER = "^((19|20)\\d\\d)(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$";
    private final static String DATE_PATTERN = "^((19|20)\\d\\d)([-])(0[1-9]|1[012])([-])(0[1-9]|[12][0-9]|3[01])$";
    private final static String DATE_UK_PATTERN = "^((19|20)\\d\\d)\\-([0-9uU][0-9kK])\\-([0-9uU][0-9kK])$";
    private final static String TIME_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    /**
     * 미래 날짜인제 체크한다. date가 null인 경우 true 반환.
     * @param date
     * @return
     */
    public static boolean isFutureDate(Date date) {
        if(!isEmpty(date)) {
            DateTime start = new DateTime(new Date());
            DateTime end = new DateTime(date);
            return start.isBefore(end);
        }

        return true;
    }

    public static boolean isPastDate(Date date) {
        if(!isEmpty(date)) {
            DateTime start = new DateTime(new Date());
            DateTime end = new DateTime(date);
            return start.isAfter(end);
        }

        return false;
    }



    public static boolean compareLocalDates(LocalDate pastLocalDate) {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate todayLocalDate = LocalDate.now();
        int dateDifference = todayLocalDate.compareTo(pastLocalDate);
        log.debug("Today : {}, Past : {}, dateDifference = {}", sdf.format(todayLocalDate), sdf.format(pastLocalDate), dateDifference);
        if(dateDifference >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDateValid(String dateToValidate, String dateFormat){
        log.debug("dateToValidate = {}, format={}", dateToValidate, dateFormat);
        if(dateToValidate == null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            log.debug("date = {}", date);

        } catch (ParseException e) {
            log.error("Error : {}", e);
            return false;
        }

        return true;
    }

    public static Date toDate(String dateToValidate, String dateFormat){
        log.trace("dateToValidate = {}, format={}", dateToValidate, dateFormat);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        sdf.setLenient(false);

        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            log.debug("date = {}", date);

            return date;
        } catch (ParseException e) {
            log.error("Error : {}", e);
            return null;
        }

    }

    public static boolean isDateValid(String date) {
        return isDateValid(date, YYYY_MM_DD);
    }

    public static Date toDate(String date) {
        return toDate(date, YYYY_MM_DD);
    }

    public static boolean isDateFormatOnlyNumber(String value) {
        Pattern p = Pattern.compile(DATE_PATTERN_ONLY_NUMBER);
        Matcher m = p.matcher(value);
        boolean isMatch = m.matches();
        log.debug(String.format("%s, %s, %s", DATE_PATTERN_ONLY_NUMBER, value, isMatch));

        return isMatch;
    }

    public static boolean isTimeFormat(String value) {
        Pattern p = Pattern.compile(TIME_PATTERN);
        Matcher m = p.matcher(value);
        boolean isMatch = m.matches();
        log.debug(String.format("%s, %s, %s", TIME_PATTERN, value, isMatch));
        log.debug("time pattern {}, value = {}, isMatch = {}", TIME_PATTERN, value, isMatch);

        return isMatch;
    }

    public static boolean isDateFormat(String value) {
        Pattern p = Pattern.compile(DATE_PATTERN);
        Matcher m = p.matcher(value);
        boolean isMatch = m.matches();
        log.debug(String.format("%s, %s, %s", DATE_PATTERN, value, isMatch));

        return isMatch;
    }

    public static Date addDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);

        return new Date(cal.getTimeInMillis());
    }

    public static Date addDay(String format, String date, int day) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return addDay(dateFormat.parse(date), day);
    }

    public static String format(Date date, String format) {
        if(ObjectUtils.isEmpty(date)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(date);
    }

    public static Date truncate(Date date) {
        String format = "yyyy-MM-dd";
        String strDate = format(date, format);
        return toDate(strDate, format);
    }

    public static long diff(Date firstDate, Date secondDate) {
        long diffInMillies = secondDate.getTime() - firstDate.getTime();
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }

//    public static void main(String[] args) {
//        System.out.println(diff(toDate("31-Oct-2019", "dd-MMM-yyyy"), toDate("31-Oct-2019", "dd-MMM-yyyy")));
//    }
}