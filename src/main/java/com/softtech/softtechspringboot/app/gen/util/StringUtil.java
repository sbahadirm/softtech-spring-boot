package com.softtech.softtechspringboot.app.gen.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public class StringUtil {

    public static String getRandomNumberAsString(int charCount){

        String randomNumeric = RandomStringUtils.randomNumeric(charCount);

        return randomNumeric;
    }

    public static Long getRandomNumber(int charCount){

        String randomNumeric = RandomStringUtils.randomNumeric(charCount);

        return Long.parseLong(randomNumeric);
    }

    public static String getRandomString(int charCount){

        String randomAlphabetic = RandomStringUtils.randomAlphabetic(charCount);

        return randomAlphabetic;
    }
}
