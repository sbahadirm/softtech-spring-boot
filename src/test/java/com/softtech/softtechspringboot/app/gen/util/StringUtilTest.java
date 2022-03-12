package com.softtech.softtechspringboot.app.gen.util;

import com.softtech.softtechspringboot.app.gen.enums.GenErrorMessage;
import com.softtech.softtechspringboot.app.gen.exceptions.GenBusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
class StringUtilTest {

    @Test
    void shouldGetRandomNumber() {

        int charCount = 5;

        Long randomNumber = StringUtil.getRandomNumber(charCount);

        assertEquals(charCount, String.valueOf(randomNumber).length());
    }

    @Test
    void shouldNotGetRandomNumberWhenCharCountIsZero() {

        int charCount = 0;

        Long randomNumber = StringUtil.getRandomNumber(charCount);

        assertNull(randomNumber);
    }

    @Test
    void shouldNotGetRandomNumberWhenCharCountTooLong() {

        int charCount = 50;

        assertThrows(NumberFormatException.class, () -> StringUtil.getRandomNumber(charCount));

    }

    @Test
    void shouldGetRandomNumberAsString() {

        int charCount = 5;

        String randomNumberAsString = StringUtil.getRandomNumberAsString(charCount);

        assertEquals(charCount, randomNumberAsString.length());
    }

    @Test
    void shouldNotGetRandomNumberAsStringWhenCharCountIsNegative() {

        int charCount = -1;

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class, () -> StringUtil.getRandomNumberAsString(charCount));

        assertEquals(GenErrorMessage.VALUE_CANNOT_BE_NEGATIVE, genBusinessException.getBaseErrorMessage());
    }

    @Test
    void shouldGetRandomNumberAsStringWhenCharCountIsZero() {

        int charCount = 0;

        String randomNumberAsString = StringUtil.getRandomNumberAsString(charCount);

        assertEquals("", randomNumberAsString);
    }

    @Test
    void getRandomString() {
    }
}