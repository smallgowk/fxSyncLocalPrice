/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.utils;

import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class StringUtils {

    public static String getFirstCapitalWord(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        input = input.trim();

        char c = input.charAt(0);

        if (c >= 97 && c <= 122) {
            c -= 32;
            return c + input.substring(1, input.length());
        }

        return input;
    }

    public static String getPrefixCapitalWord(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        input = input.trim();

        StringBuilder sb = new StringBuilder();
        char firstChar = input.charAt(0);
        if (firstChar >= 97 && firstChar <= 122) {
            firstChar -= 32;
            sb.append(firstChar);
        } else if ((firstChar >= 48 && firstChar <= 57) || (firstChar >= 65 && firstChar <= 90)) {
            sb.append(firstChar);
        }

        for (int i = 1, length = input.length(); i < length; i++) {
            char c = input.charAt(i);
            sb.append(c);
            if (c == 32) {
                char nextChar = input.charAt(i + 1);
                if (nextChar >= 97 && nextChar <= 122) {
                    nextChar -= 32;
                    sb.append(nextChar);
                    i++;
                }
            }
        }

        return sb.toString();
    }

    public static String getStringBrief(String input) {
        StringBuilder sb = new StringBuilder();
        sb.append(input.charAt(0));
        for (int i = 1, length = input.length(); i < length; i++) {
            char c = input.charAt(i);
            if (c >= 65 && c <= 90) {
                sb.append(c);
            } else {
                if (c == 32) {
                    if (i < length - 1) {
                        char nextChar = input.charAt(i + 1);
                        if (nextChar >= 65 && nextChar <= 90) {
                            sb.append(nextChar);
                        }

                        i++;
                    }
                }
            }
        }

        if (sb.length() == 1) {
            if (input.length() <= 5) {
                return input;
            }
            for (int i = 0; i < 5; i++) {
                char c = input.charAt(i);
                if (c != 32) {
                    sb.append(c);
                }
            }
        }

        return sb.toString();
    }

    public static boolean isValidWord(String input) {

        if (input == null || input.length() <= 2) {
            return false;
        }

        Pattern pattern = Pattern.compile("[A-Za-z]+");
        return pattern.matcher(input).matches();
    }
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
}
