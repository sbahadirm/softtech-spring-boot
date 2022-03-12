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
    void shouldNotGetRandomNumberWhenCharCountIsNegative() {

        int charCount = -1;

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class, () -> StringUtil.getRandomNumber(charCount));

        assertEquals(GenErrorMessage.VALUE_CANNOT_BE_NEGATIVE, genBusinessException.getBaseErrorMessage());

    }

    @Test
    void shouldNotGetRandomNumberWhenCharCountTooLong() {

        int charCount = 50;

        assertThrows(NumberFormatException.class, () -> StringUtil.getRandomNumber(charCount));

    }

    @Test
    void getRandomNumberAsString() {
    }

    @Test
    void getRandomString() {
    }
}