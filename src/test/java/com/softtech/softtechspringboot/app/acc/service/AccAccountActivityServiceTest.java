package com.softtech.softtechspringboot.app.acc.service;

import com.softtech.softtechspringboot.app.acc.dto.AccAccountActivityDto;
import com.softtech.softtechspringboot.app.acc.dto.AccMoneyActivityDto;
import com.softtech.softtechspringboot.app.acc.dto.AccMoneyActivityRequestDto;
import com.softtech.softtechspringboot.app.acc.entity.AccAccount;
import com.softtech.softtechspringboot.app.acc.entity.AccAccountActivity;
import com.softtech.softtechspringboot.app.acc.enums.AccAccountActivityType;
import com.softtech.softtechspringboot.app.acc.enums.AccErrorMessage;
import com.softtech.softtechspringboot.app.acc.service.entityservice.AccAccountActivityEntityService;
import com.softtech.softtechspringboot.app.acc.service.entityservice.AccAccountEntityService;
import com.softtech.softtechspringboot.app.gen.enums.GenErrorMessage;
import com.softtech.softtechspringboot.app.gen.exceptions.GenBusinessException;
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

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accountId)
                .amount(amount)
                .activityType(activityType)
                .build();

        doReturn(accAccountActivity).when(accAccountActivityService).moneyOut(accMoneyActivityDto);

        AccAccountActivityDto result = accAccountActivityService.withdraw(accMoneyActivityRequestDto);

        assertEquals(amount, result.getCurrentBalance());

        verify(accAccountActivityService).moneyOut(accMoneyActivityDto);
    }

    @Test
    void shouldNotWithdrawWhenParameterIsNull() {

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.withdraw(null));

        assertEquals(GenErrorMessage.PARAMETER_CANNOT_BE_NULL, genBusinessException.getBaseErrorMessage());

    }

    @Test
    void shouldDeposit() {

        Long accountId = 1L;
        BigDecimal amount = new BigDecimal(100);
        AccAccountActivityType activityType = AccAccountActivityType.DEPOSIT;

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);
        when(accMoneyActivityRequestDto.getAccAccountId()).thenReturn(accountId);
        when(accMoneyActivityRequestDto.getAmount()).thenReturn(amount);

        AccAccountActivity accAccountActivity = mock(AccAccountActivity.class);
        when(accAccountActivity.getCurrentBalance()).thenReturn(amount);

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accountId)
                .amount(amount)
                .activityType(activityType)
                .build();

        doReturn(accAccountActivity).when(accAccountActivityService).moneyIn(accMoneyActivityDto);

        AccAccountActivityDto result = accAccountActivityService.deposit(accMoneyActivityRequestDto);

        assertEquals(amount, result.getCurrentBalance());

        verify(accAccountActivityService).moneyIn(accMoneyActivityDto);
    }

    @Test
    void shouldNotDepositWhenParameterIsNull() {

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.deposit(null));

        assertEquals(GenErrorMessage.PARAMETER_CANNOT_BE_NULL, genBusinessException.getBaseErrorMessage());

    }

    @Test
    void shouldMoneyOut() {

        Long accountId = 1L;
        BigDecimal amount = new BigDecimal(100);
        BigDecimal currentBalance = new BigDecimal(1000);
        BigDecimal newBalance = currentBalance.subtract(amount);
        AccAccountActivityType activityType = AccAccountActivityType.WITHDRAW;

        AccAccount accAccount = mock(AccAccount.class);
        when(accAccount.getCurrentBalance()).thenReturn(currentBalance);

        AccAccountActivity accAccountActivity = mock(AccAccountActivity.class);
        when(accAccountActivity.getCurrentBalance()).thenReturn(newBalance);

        when(accAccountEntityService.getByIdWithControl(accountId)).thenReturn(accAccount);
        when(accAccountActivityEntityService.save(any())).thenReturn(accAccountActivity);
        when(accAccountEntityService.save(accAccount)).thenReturn(accAccount);

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accountId)
                .amount(amount)
                .activityType(activityType)
                .build();

        AccAccountActivity result = accAccountActivityService.moneyOut(accMoneyActivityDto);

        assertEquals(newBalance, result.getCurrentBalance());
    }

    @Test
    void shouldNotMoneyOutWhenParameterIsNull() {

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.moneyOut(null));

        assertEquals(GenErrorMessage.PARAMETER_CANNOT_BE_NULL, genBusinessException.getBaseErrorMessage());
    }

    @Test
    void shouldNotMoneyOutWhenBalanceIsInsufficient() {

        Long accountId = 1L;
        BigDecimal amount = new BigDecimal(2000);
        BigDecimal currentBalance = new BigDecimal(1000);
        BigDecimal newBalance = currentBalance.subtract(amount);
        AccAccountActivityType activityType = AccAccountActivityType.WITHDRAW;

        AccAccount accAccount = mock(AccAccount.class);
        when(accAccount.getCurrentBalance()).thenReturn(currentBalance);

        when(accAccountEntityService.getByIdWithControl(accountId)).thenReturn(accAccount);

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accountId)
                .amount(amount)
                .activityType(activityType)
                .build();

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.moneyOut(accMoneyActivityDto));

        assertEquals(AccErrorMessage.INSUFFICIENT_BALANCE, genBusinessException.getBaseErrorMessage());
    }

    @Test
    void shouldMoneyIn() {

        Long accountId = 1L;
        BigDecimal amount = new BigDecimal(100);
        BigDecimal currentBalance = new BigDecimal(1000);
        BigDecimal newBalance = currentBalance.add(amount);
        AccAccountActivityType activityType = AccAccountActivityType.DEPOSIT;

        AccAccount accAccount = mock(AccAccount.class);
        when(accAccount.getCurrentBalance()).thenReturn(currentBalance);

        AccAccountActivity accAccountActivity = mock(AccAccountActivity.class);
        when(accAccountActivity.getCurrentBalance()).thenReturn(newBalance);

        when(accAccountEntityService.getByIdWithControl(accountId)).thenReturn(accAccount);
        when(accAccountActivityEntityService.save(any())).thenReturn(accAccountActivity);
        when(accAccountEntityService.save(accAccount)).thenReturn(accAccount);

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accountId)
                .amount(amount)
                .activityType(activityType)
                .build();

        AccAccountActivity result = accAccountActivityService.moneyIn(accMoneyActivityDto);

        assertEquals(newBalance, result.getCurrentBalance());
    }

    @Test
    void shouldNotMoneyInWhenParameterIsNull() {

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.moneyIn(null));

        assertEquals(GenErrorMessage.PARAMETER_CANNOT_BE_NULL, genBusinessException.getBaseErrorMessage());
    }

}