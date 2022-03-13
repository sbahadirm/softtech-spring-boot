package com.softtech.softtechspringboot.app.cus.service;

import com.softtech.softtechspringboot.app.cus.converter.CusCustomerConverter;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerDto;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerSaveRequestDto;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerUpdateRequestDto;
import com.softtech.softtechspringboot.app.cus.entity.CusCustomer;
import com.softtech.softtechspringboot.app.cus.enums.CusErrorMessage;
import com.softtech.softtechspringboot.app.cus.service.entityservice.CusCustomerEntityService;
import com.softtech.softtechspringboot.app.gen.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

//        try (MockedStatic<CusCustomerMapper> theMock = Mockito.mockStatic(CusCustomerMapper.class)) {
//            theMock.when(() -> CusCustomerMapper.convertToCusCustomer(cusCustomerSaveRequestDto))
//                    .thenReturn(cusCustomer);

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
    void shouldDelete() {

        CusCustomer cusCustomer = mock(CusCustomer.class);

        when(cusCustomerEntityService.getByIdWithControl(anyLong())).thenReturn(cusCustomer);

        cusCustomerService.delete(anyLong());

        verify(cusCustomerEntityService).getByIdWithControl(anyLong());
        verify(cusCustomerEntityService).delete(any());
    }

    @Test
    void shouldNotDeleteWhenIdDoesNotExist() {

        when(cusCustomerEntityService.getByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> cusCustomerService.delete(anyLong()));

        verify(cusCustomerEntityService).getByIdWithControl(anyLong());
    }

    @Test
    void shouldFindById() {

        Long id = 18L;

        CusCustomer cusCustomer = mock(CusCustomer.class);
        when(cusCustomer.getId()).thenReturn(id);

        when(cusCustomerEntityService.getByIdWithControl(id)).thenReturn(cusCustomer);

        CusCustomerDto cusCustomerDto = cusCustomerService.findById(id);

        assertEquals(id, cusCustomerDto.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist() {

        when(cusCustomerEntityService.getByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> cusCustomerService.findById(anyLong()));

        verify(cusCustomerEntityService).getByIdWithControl(anyLong());
    }

    @Test
    void shouldUpdate() {

        Long id = 18L;

        CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto = mock(CusCustomerUpdateRequestDto.class);
        CusCustomer cusCustomer = mock(CusCustomer.class);
        when(cusCustomer.getId()).thenReturn(id);

        boolean isExist = true;

        when(cusCustomerEntityService.existsById(anyLong())).thenReturn(isExist);
        when(cusCustomerEntityService.save(any())).thenReturn(cusCustomer);

        CusCustomerDto cusCustomerDto = cusCustomerService.update(cusCustomerUpdateRequestDto);

        assertEquals(id, cusCustomerDto.getId());

        verify(cusCustomerEntityService).existsById(anyLong());
    }

    @Test
    void shouldNotUpdateWhenCustomerDoesNotExist() {

        CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto = mock(CusCustomerUpdateRequestDto.class);

        when(cusCustomerEntityService.existsById(anyLong())).thenThrow(ItemNotFoundException.class);

        ItemNotFoundException itemNotFoundException = assertThrows(ItemNotFoundException.class, () -> cusCustomerService.update(cusCustomerUpdateRequestDto));

        verify(cusCustomerEntityService).existsById(anyLong());
    }

    @Test
    void shouldNotUpdateWhenCustomerDoesNotExist2() {

        CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto = mock(CusCustomerUpdateRequestDto.class);

        when(cusCustomerEntityService.existsById(anyLong())).thenReturn(false);

        ItemNotFoundException itemNotFoundException = assertThrows(ItemNotFoundException.class, () -> cusCustomerService.update(cusCustomerUpdateRequestDto));
        assertEquals(CusErrorMessage.CUSTOMER_ERROR_MESSAGE, itemNotFoundException.getBaseErrorMessage());

        verify(cusCustomerEntityService).existsById(anyLong());
    }
}