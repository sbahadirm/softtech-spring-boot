package com.softtech.softtechspringboot.app.reflection;

import com.softtech.softtechspringboot.app.acc.dto.AccAccountDto;
import com.softtech.softtechspringboot.app.acc.entity.AccAccount;

import java.lang.reflect.Method;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public class CodeGenerator {


    /**
     *         CustomerDto target = new CustomerDto();
     *         target.setId(source.getId());
     *         target.setName(source.getName());
     *         target.setSurname(source.getSurname());
     *         return target;
     * @param args
     */
    public static void main(String[] args) {

        Class clazzSource = AccAccount.class;
        Class clazzTarget = AccAccountDto.class;

        String simpleName = clazzTarget.getSimpleName();
        Method[] declaredMethods = clazzTarget.getDeclaredMethods();

        System.out.println(simpleName + " target = new " + simpleName + "();");

        for (Method eachMethod : declaredMethods) {
            String name = eachMethod.getName();

            if (name.startsWith("set")){

                String getName = name.replace("set", "get");

                System.out.println("target." + name + "(source." + getName + "());");
            }
        }
        System.out.println("return target;");


    }
}
