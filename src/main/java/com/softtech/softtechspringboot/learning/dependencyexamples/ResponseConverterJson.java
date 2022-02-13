package com.softtech.softtechspringboot.learning.dependencyexamples;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Component
@Primary
@Qualifier("json")
public class ResponseConverterJson implements ResponseConverter {
    @Override
    public void convert() {
        System.out.println("JSON");
    }
}
