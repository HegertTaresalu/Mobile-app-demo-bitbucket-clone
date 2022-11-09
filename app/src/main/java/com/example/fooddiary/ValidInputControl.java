package com.example.fooddiary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidInputControl {

    public static boolean isValidEmail(String email){
        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-z]+";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();

    }

    public static boolean  isValidPassword(String password){
        Pattern pattern;
        Matcher matcher;


        final String PASSWORD_PATTERN =
                "^" +
                        "(?=.*[0-9])" +         //at least 1 digit
                        "(?=.*[a-z])" +         //at least 1 lowercase letter
                        "(?=.*[A-Z])" +         //at least 1 uppercase letter
                        "(?=\\S+$)" +           //no whitespaces
                        ".{8,20}" +               //at least 8 to 20 characters
                        "$";
        pattern =  Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);


        return matcher.matches();
    }


}
