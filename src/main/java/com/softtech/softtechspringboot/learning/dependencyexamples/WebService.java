package com.softtech.softtechspringboot.learning.dependencyexamples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@Scope("singleton")
public class WebService {

    @Autowired
    @Qualifier("xml")
    private ResponseConverter responseConverterJson;

    public void convertResponse(){
        responseConverterJson.convert();
    }

    public ResponseConverter getResponseConverterJson() {
        return responseConverterJson;
    }
}
