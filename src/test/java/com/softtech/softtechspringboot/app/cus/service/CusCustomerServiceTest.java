package com.softtech.softtechspringboot.app.cus.service;

import com.softtech.softtechspringboot.app.cus.converter.CusCustomerConverter;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerDto;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerSaveRequestDto;
import com.softtech.softtechspringboot.app.cus.entity.CusCustomer;
import com.softtech.softtechspringboot.app.cus.service.entityservice.CusCustomerEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class CusCustomerServiceTest {

    @Mock
    private CusCustomerEntityService cusCustomerEntityService;

    @Mock
    private CusCustomerConverter cusCustomerConverter;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CusCustomerService cusCustomerService;

    @Test
    void shouldFindAll() {

        CusCustomer cusCustomer = mock(CusCustomer.class);
        List<CusCustomer>  cusCustomerList = new ArrayList<>();
        cusCustomerList.add(cusCustomer);

        CusCustomerDto cusCustomerDto = mock(CusCustomerDto.class);
        List<CusCustomerDto>  cusCustomerDtoList = new ArrayList<>();
        cusCustomerDtoList.add(cusCustomerDto);

        when(cusCustomerEntityService.findAll()).thenReturn(cusCustomerList);
        when(cusCustomerConverter.convertToCusCustomerDtoList(cusCustomerList)).thenReturn(cusCustomerDtoList);

        List<CusCustomerDto> result = cusCustomerService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindAllWhenCustomerListIsEmpty() {

        List<CusCustomer> cusCustomerList = new ArrayList<>();
        List<CusCustomerDto> cusCustomerDtoList = new ArrayList<>();

        when(cusCustomerEntityService.findAll()).thenReturn(cusCustomerList);
        when(cusCustomerConverter.convertToCusCustomerDtoList(cusCustomerList)).thenReturn(cusCustomerDtoList);

        List<CusCustomerDto> result = cusCustomerService.findAll();

        assertEquals(cusCustomerDtoList.size(), result.size());
    }

    @Test
    void shouldFindAllWhenCustomerListIsNull() {

        when(cusCustomerEntityService.findAll()).thenReturn(null);
        when(cusCustomerConverter.convertToCusCustomerDtoList(null)).thenCallRealMethod();

        assertThrows(NullPointerException.class, () -> cusCustomerService.findAll());
    }

    @Test
    void shouldSave() {

        CusCustomerSaveRequestDto cusCustomerSaveRequestDto = mock(CusCustomerSaveRequestDto.class);
        when(cusCustomerSaveRequestDto.getPassword()).thenReturn("123");

        CusCustomer cusCustomer = mock(CusCustomer.class);
        when(cusCustomer.getId()).thenReturn(1L);

        when(passwordEncoder.encode(anyString())).thenReturn("bahadir_1234_123");
        when(cusCustomerEntityService.save(any())).thenReturn(cusCustomer);

        CusCustomerDto result = cusCustomerService.save(cusCustomerSaveRequestDto);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldNotSaveWhenParameterIsNull() {

        assertThrows(NullPointerException.class, () -> cusCustomerService.save(null));

    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }
}