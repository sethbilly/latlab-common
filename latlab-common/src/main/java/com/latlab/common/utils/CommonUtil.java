/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public class CommonUtil {

    public static boolean isEmailValid(String email) {
        try {
            if (email.isEmpty()) {
                return false;
            }
            String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_'{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

//            Pattern p = Pattern.compile("[a-zA-Z0-9_\\-]+[\\.[a-zA-Z0-9]+]*@[a-zA-Z0-9_\\-]+\\.[a-zA-Z]{2,3}(\\.[a-zA-Z]{2,2})?");
            Pattern p = Pattern.compile(pattern);

            Matcher m = p.matcher(email);
            return m.matches();
        } catch (Exception e) {
        }

        return false;
    }

    public void isPasswordValid(String password, int minimumLenght) {
        boolean valid = true;
        if (password.length() < minimumLenght) {
            System.out.println("Password should not be less than 8 characters in length.");
            valid = false;
        }

        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars)) {
            System.out.println("Password should contain at least one upper case alphabet");
            valid = false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars)) {
            System.out.println("Password should contain atleast one lower case alphabet");
            valid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers)) {
            System.out.println("Password should contain at least one number.");
            valid = false;
        }
        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (!password.matches(specialChars)) {
            System.out.println("Password should contain atleast one special character");
            valid = false;
        }
        if (valid) {
            System.out.println("Password is valid.");
        }
    }
}
