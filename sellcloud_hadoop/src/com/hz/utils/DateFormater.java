package com.hz.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormater {
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static String format(LocalDateTime date){
        return date.format(formatter);
    }

    public static LocalDateTime parse(String date){
        return LocalDateTime.parse(date,formatter);
    }
}
