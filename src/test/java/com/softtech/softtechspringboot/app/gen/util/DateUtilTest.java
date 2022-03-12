package com.softtech.softtechspringboot.app.gen.util;

import com.softtech.softtechspringboot.app.gen.enums.GenErrorMessage;
import com.softtech.softtechspringboot.app.gen.exceptions.GenBusinessException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
class DateUtilTest {

    private static SimpleDateFormat formatterDate;
    private static SimpleDateFormat formatterDateTime;

    @BeforeAll
    public static void setUp(){
        formatterDate = new SimpleDateFormat("dd-MM-yyyy");
        formatterDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    @Test
    void shouldConvertToLocalDate() throws ParseException {

        Date date = formatterDate.parse("05-10-1991");

        LocalDate localDate = DateUtil.convertToLocalDate(date);

        assertEquals(5, localDate.getDayOfMonth());
        assertEquals(10, localDate.getMonthValue());
        assertEquals(1991, localDate.getYear());
    }

    @Test
    void shouldNotConvertToLocalDateWhenParameterIsNull() {

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class, () -> DateUtil.convertToLocalDate(null));

        assertEquals(genBusinessException.getBaseErrorMessage(), GenErrorMessage.DATE_COULD_NOT_BE_CONVERTED);
        org.assertj.core.api.Assertions.assertThat(genBusinessException.getBaseErrorMessage()).isEqualTo(GenErrorMessage.DATE_COULD_NOT_BE_CONVERTED);
    }

    @Test
    void shouldConvertToLocalDateWhen29Feb() throws ParseException {

        Date date = formatterDate.parse("29-02-2016");

        LocalDate localDate = DateUtil.convertToLocalDate(date);

        assertEquals(29, localDate.getDayOfMonth());
        assertEquals(02, localDate.getMonthValue());
        assertEquals(2016, localDate.getYear());
    }

    @Test
    void shouldConvertToLocalDateWhen01Jan() throws ParseException {

        Date date = formatterDate.parse("01-01-2016");

        LocalDate localDate = DateUtil.convertToLocalDate(date);

        assertEquals(1, localDate.getDayOfMonth());
        assertEquals(1, localDate.getMonthValue());
        assertEquals(2016, localDate.getYear());
    }

    @Test
    void shouldConvertToLocalDateWhen31Dec() throws ParseException {

        Date date = formatterDate.parse("31-12-2016");

        LocalDate localDate = DateUtil.convertToLocalDate(date);

        assertEquals(31, localDate.getDayOfMonth());
        assertEquals(12, localDate.getMonthValue());
        assertEquals(2016, localDate.getYear());
    }

    @Test
    void shouldConvertToLocalDateTime() throws ParseException {

        Date date = formatterDateTime.parse("05-10-1991 10:11:12");

        LocalDateTime localDateTime = DateUtil.convertToLocalDateTime(date);

        assertEquals(5, localDateTime.getDayOfMonth());
        assertEquals(10, localDateTime.getMonthValue());
        assertEquals(1991, localDateTime.getYear());
        assertEquals(10, localDateTime.getHour());
        assertEquals(11, localDateTime.getMinute());
        assertEquals(12, localDateTime.getSecond());
    }

    @Test
    void shouldNotConvertToLocalDateTimeWhenParameterIsNull() {

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class, () -> DateUtil.convertToLocalDateTime(null));
        assertEquals(genBusinessException.getBaseErrorMessage(), GenErrorMessage.DATE_COULD_NOT_BE_CONVERTED);
    }

    @Test
    void shouldConvertToLocalDateTimeWhenTimeIs000000() throws ParseException {

        Date date = formatterDateTime.parse("05-10-1991 00:00:00");

        LocalDateTime localDateTime = DateUtil.convertToLocalDateTime(date);

        assertEquals(5, localDateTime.getDayOfMonth());
        assertEquals(10, localDateTime.getMonthValue());
        assertEquals(1991, localDateTime.getYear());
        assertEquals(0, localDateTime.getHour());
        assertEquals(0, localDateTime.getMinute());
        assertEquals(0, localDateTime.getSecond());
    }

    @Test
    void shouldConvertToLocalDateTimeWhenTimeIs235959() throws ParseException {

        Date date = formatterDateTime.parse("05-10-1991 23:59:59");

        LocalDateTime localDateTime = DateUtil.convertToLocalDateTime(date);

        assertEquals(5, localDateTime.getDayOfMonth());
        assertEquals(10, localDateTime.getMonthValue());
        assertEquals(1991, localDateTime.getYear());
        assertEquals(23, localDateTime.getHour());
        assertEquals(59, localDateTime.getMinute());
        assertEquals(59, localDateTime.getSecond());
    }

    @Test
    void shouldConvertToDate() {

        LocalDate localDate = LocalDate.of(1991, 10, 5);

        Date date = DateUtil.convertToDate(localDate);

        String format = formatterDate.format(date);

        assertEquals("05-10-1991", format);
    }

    @Test
    void shouldNotConvertToDateWhenParameterIsNull() {

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class, () -> DateUtil.convertToDate(null));

        assertEquals(GenErrorMessage.DATE_COULD_NOT_BE_CONVERTED, genBusinessException.getBaseErrorMessage());
    }
}