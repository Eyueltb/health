package com.health.dto;

import java.util.Arrays;

/**
 * A function that return a string describing first character of the given string:
 *          "digit" for a digit,
 *           "lower" for a lowercase letter,
 *           "upper" for an uppercase letter
 *           and "other" for other characters.
 *  You can assume the characters are ASCII.
 */
public class ValidateFirstCharacter {

    public static String solution(String s) {
        char c = s.charAt(0);
        if (Character.isUpperCase(c)) {  // please fix condition
            return "upper";
        } else if (Character.isLowerCase(c))  {  // please fix condition
            return "lower";
        } else if (Character.isDigit(c)) {  // please fix condition
            return "digit";
        } else {
            return "other";
        }
    }
    public static String validateFirstCharacter(String s) {
        char ch = s.charAt(0);
        return Character.isUpperCase(ch) ? "upper"
                : Character.isLowerCase(ch) ? "lower"
                : Character.isDigit(ch) ? "digit"
                : "other";
    }

    public static void main(String[] args) {
        String [ ] str = {"Hello", "hell0", "123", "እዩኤል"};
        Arrays.stream(str)
                .map(ValidateFirstCharacter::validateFirstCharacter)
                .forEach(System.out::println);
   }
}
