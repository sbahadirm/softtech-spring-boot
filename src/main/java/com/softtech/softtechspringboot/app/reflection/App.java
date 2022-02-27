package com.softtech.softtechspringboot.app.reflection;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public class App {

    public static void main(String[] args) {

        Class<?> clazz;
        try {
            clazz = Class.forName("com.softtech.softtechspringboot.app.reflection.CustomerDto");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("sdsds");
        }

        System.out.println(clazz);
    }
}
