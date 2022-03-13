package com.softtech.softtechspringboot.app.cus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerSaveRequestDto;
import com.softtech.softtechspringboot.app.gen.dto.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CusCustomerControllerTest {

    private static final String BASE_PATH = "/api/v1/customers";

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void findAll() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        RestResponse restResponse = objectMapper.readValue(result.getResponse().getContentAsString(), RestResponse.class);

        boolean isSuccess = restResponse.isSuccess();

        assertTrue(isSuccess);

    }

    @Test
    void save() throws Exception {

        CusCustomerSaveRequestDto cusCustomerSaveRequestDto = CusCustomerSaveRequestDto.builder()
                .name("erdem")
                .surname("özoğlu")
                .identityNo(12312312389L)
                .password("12345678")
                .build();

//        String content = "{\n" +
//                "  \"name\": \"john\",\n" +
//                "  \"surname\": \"smith\",\n" +
//                "  \"identityNo\": 92345678901,\n" +
//                "  \"password\": \"J.s_1234\"\n" +
//                "}";

        String content = objectMapper.writeValueAsString(cusCustomerSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        RestResponse restResponse = objectMapper.readValue(result.getResponse().getContentAsString(), RestResponse.class);

        boolean isSuccess = restResponse.isSuccess();

        assertTrue(isSuccess);

    }

    @Test
    void findById() {
    }



    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}