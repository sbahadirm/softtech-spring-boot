package com.softtech.softtechspringboot.app.acc.service;

import com.softtech.softtechspringboot.app.acc.dto.AccAccountActivityDto;
import com.softtech.softtechspringboot.app.acc.dto.AccMoneyActivityRequestDto;
import com.softtech.softtechspringboot.app.acc.entity.AccAccountActivity;
import com.softtech.softtechspringboot.app.acc.enums.AccAccountActivityType;
import com.softtech.softtechspringboot.app.acc.service.entityservice.AccAccountActivityEntityService;
import com.softtech.softtechspringboot.app.acc.service.entityservice.AccAccountEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class AccAccountActivityServiceTest {

    @Spy
    @InjectMocks
    private AccAccountActivityService accAccountActivityService;

    @Mock
    private AccAccountEntityService accAccountEntityService;

    @Mock
    private AccAccountActivityEntityService accAccountActivityEntityService;

    @Test
    void shouldWithdraw() {

        Long accountId = 1L;
        BigDecimal amount = new BigDecimal(100);
        AccAccountActivityType activityType = AccAccountActivityType.WITHDRAW;

        AccAccountActivity accAccountActivity = mock(AccAccountActivity.class);
        when(accAccountActivity.getCurrentBalance()).thenReturn(amount);

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);
        when(accMoneyActivityRequestDto.getAccAccountId()).thenReturn(accountId);
        when(accMoneyActivityRequestDto.getAmount()).thenReturn(amount);

        doReturn(accAccountActivity).when(accAccountActivityService).moneyOut(accountId, amount, activityType);

        AccAccountActivityDto result = accAccountActivityService.withdraw(accMoneyActivityRequestDto);

        assertEquals(amount, result.getCurrentBalance());

        verify(accAccountActivityService).moneyOut(accountId, amount, activityType);
    }

    @Test
    void deposit() {


    }

    @Test
    void moneyOut() {

//        accAccountActivityService.moneyOut()
    }

    @Test
    void moneyIn() {
    }
}