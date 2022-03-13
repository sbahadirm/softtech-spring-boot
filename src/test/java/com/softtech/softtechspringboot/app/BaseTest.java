package com.softtech.softtechspringboot.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softtech.softtechspringboot.app.gen.dto.RestResponse;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public class BaseTest {

    protected ObjectMapper objectMapper;

    protected boolean isSuccess(MvcResult result) throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        RestResponse restResponse = getRestResponse(result);

        return isSuccess(restResponse);
    }

    protected RestResponse getRestResponse(MvcResult result) throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(result.getResponse().getContentAsString(), RestResponse.class);
    }

    private boolean isSuccess(RestResponse restResponse) {
        return restResponse.isSuccess();
    }

}
