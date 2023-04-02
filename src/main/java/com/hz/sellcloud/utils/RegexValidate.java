package com.hz.sellcloud.utils;

import javafx.scene.shape.PathElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidate {
    static String PHONE = "^1\\d{10}$";

    static String EMAIL = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";

    public static boolean isPhone(String phone){
        Pattern pattern = Pattern.compile(PHONE);
        return pattern.matcher(phone).matches();
    }

    public static boolean isEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL);
        return pattern.matcher(email).matches();
    }
}
