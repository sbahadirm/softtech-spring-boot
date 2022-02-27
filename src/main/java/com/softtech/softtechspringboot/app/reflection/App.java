package com.softtech.softtechspringboot.app.reflection;

import java.lang.reflect.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public class App {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {

        Class<?> clazz = Class.forName("com.softtech.softtechspringboot.app.reflection.CustomerDto");

        Constructor<?> constructorWithNoArgs = clazz.getConstructor();
        Object instance = constructorWithNoArgs.newInstance();

        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field declaredField : declaredFields) {

            String name = declaredField.getName();

            declaredField.setAccessible(true);

            if (declaredField.getType().equals(String.class)){
                declaredField.set(instance, name);
            } else {
                declaredField.set(instance, 1L);
            }
        }

        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {

            String name = declaredMethod.getName();

            if (name.startsWith("setId")) {
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(instance, 3L);
            }

        }

        for (Method declaredMethod : declaredMethods) {

            String name = declaredMethod.getName();

            if (name.startsWith("get")){
                declaredMethod.setAccessible(true);

                Object invoke = declaredMethod.invoke(instance);

                System.out.println(name + "-> " + invoke);
            }
        }

        System.out.println(clazz);
    }
}
