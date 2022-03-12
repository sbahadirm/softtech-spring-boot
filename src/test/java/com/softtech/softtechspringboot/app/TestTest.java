package com.softtech.softtechspringboot.app;

import com.softtech.softtechspringboot.app.sec.dto.SecLoginRequestDto;
import org.junit.jupiter.api.*;

/**
 * for learning how test annotations work
 *
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public class TestTest {

    private static SecLoginRequestDto secLoginRequestDto;

    @BeforeAll
    public static void setup(){
        System.out.println("setup");
    }

    private static void getDummySecLoginRequestDto() {
        secLoginRequestDto = new SecLoginRequestDto();
        secLoginRequestDto.setIdentityNo(123L);
        secLoginRequestDto.setPassword("1234");
    }

    @BeforeEach
    public void beforeEach(){
        System.out.println("before each");
        getDummySecLoginRequestDto();
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("after all");
    }

    @AfterEach
    public void afterEach(){
        System.out.println("after each");
    }

    @Test
    public void test1(){

        secLoginRequestDto.setIdentityNo(null);

        System.out.println("test1");

        Assertions.assertEquals(null, secLoginRequestDto.getIdentityNo());
    }

    @Test
    public void test2(){


        System.out.println("test2");

        Assertions.assertEquals(123L, secLoginRequestDto.getIdentityNo());

    }

    @Test
    public void test3(){
        System.out.println("test3");
    }
}
