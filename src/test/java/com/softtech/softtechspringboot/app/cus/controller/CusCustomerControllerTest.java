package com.softtech.softtechspringboot.app.cus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.softtech.softtechspringboot.SofttechSpringBootApplication;
import com.softtech.softtechspringboot.app.BaseTest;
import com.softtech.softtechspringboot.app.config.H2TestProfileJPAConfig;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerSaveRequestDto;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerUpdateRequestDto;
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
@SpringBootTest(classes = {SofttechSpringBootApplication.class, H2TestProfileJPAConfig.class})
class CusCustomerControllerTest extends BaseTest {

    private static final String BASE_PATH = "/api/v1/customers";

    private MockMvc mockMvc;

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

        boolean isSuccess = isSuccess(result);

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

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);

    }

    @Test
    void findById() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/1").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);

    }

    @Test
    void deleteTest() throws Exception {

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/2202").content("2202").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNoDeleteWhenIdDoesNotExist() throws Exception {

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/9999").content("9999").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void update() throws Exception {

        CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto = new CusCustomerUpdateRequestDto();
        cusCustomerUpdateRequestDto.setId(2052L);
        cusCustomerUpdateRequestDto.setName("test2");
        cusCustomerUpdateRequestDto.setSurname("test2");
        cusCustomerUpdateRequestDto.setIdentityNo(12345678918L);
        cusCustomerUpdateRequestDto.setPassword("abcdefgh");

        String content = objectMapper.writeValueAsString(cusCustomerUpdateRequestDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }
}