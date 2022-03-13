package com.softtech.softtechspringboot.app.acc.service;

import com.softtech.softtechspringboot.app.acc.dto.AccAccountDto;
import com.softtech.softtechspringboot.app.acc.entity.AccAccount;
import com.softtech.softtechspringboot.app.acc.service.entityservice.AccAccountEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class AccAccountServiceTest {

    @InjectMocks
    private AccAccountService accAccountService;

    @Mock
    private AccAccountEntityService accAccountEntityService;

    @Test
    void findAll() {

        AccAccount accAccount = new AccAccount();
//        when(accAccount.getCurrentBalance()).thenReturn(BigDecimal.TEN);

        accAccount.setIbanNo("12345");

        List<AccAccount> accAccountList = new ArrayList<>();
        accAccountList.add(accAccount);

        when(accAccountEntityService.findAllActiveAccAccountList()).thenReturn(accAccountList);

        List<AccAccountDto> result = accAccountService.findAll();

        assertEquals(1L, result.size());

    }
}