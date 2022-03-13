package com.softtech.softtechspringboot.app.acc.service;

import com.softtech.softtechspringboot.app.acc.converter.AccAccountMapper;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AccAccountActivityService {

    private final AccAccountEntityService accAccountEntityService;
    private final AccAccountActivityEntityService accAccountActivityEntityService;

    public AccAccountActivityDto withdraw(AccMoneyActivityRequestDto accMoneyActivityRequestDto) {

        validateAccMoneyActivityRequestDto(accMoneyActivityRequestDto);

        Long accAccountId = accMoneyActivityRequestDto.getAccAccountId();
        BigDecimal amount = accMoneyActivityRequestDto.getAmount();

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accAccountId)
                .amount(amount)
                .activityType(AccAccountActivityType.WITHDRAW)
                .build();

        AccAccountActivity accAccountActivity = moneyOut(accMoneyActivityDto);

        AccAccountActivityDto accAccountActivityDto = AccAccountMapper.INSTANCE.convertToAccAccountActivityDto(accAccountActivity);

        return accAccountActivityDto;
    }

    public AccAccountActivityDto deposit(AccMoneyActivityRequestDto accMoneyActivityRequestDto) {

        validateAccMoneyActivityRequestDto(accMoneyActivityRequestDto);

        Long accAccountId = accMoneyActivityRequestDto.getAccAccountId();
        BigDecimal amount = accMoneyActivityRequestDto.getAmount();

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accAccountId)
                .amount(amount)
                .activityType(AccAccountActivityType.DEPOSIT)
                .build();

        AccAccountActivity accAccountActivity = moneyIn(accMoneyActivityDto);

        AccAccountActivityDto accAccountActivityDto = AccAccountMapper.INSTANCE.convertToAccAccountActivityDto(accAccountActivity);

        return accAccountActivityDto;
    }

    public AccAccountActivity moneyOut(AccMoneyActivityDto accMoneyActivityDto) {

        validateAccMoneyActivityDto(accMoneyActivityDto);

        Long accountId = accMoneyActivityDto.getAccAccountId();
        BigDecimal amount = accMoneyActivityDto.getAmount();
        AccAccountActivityType activityType = accMoneyActivityDto.getActivityType();

        AccAccount accAccount = accAccountEntityService.getByIdWithControl(accountId);

        BigDecimal newBalance = accAccount.getCurrentBalance().subtract(amount);

        validateBalance(newBalance);

        AccAccountActivity accAccountActivity = createAccAccountActivity(accountId, amount, newBalance, activityType);

        updateCurrentBalance(accAccount, newBalance);

        return accAccountActivity;
    }

    public AccAccountActivity moneyIn(AccMoneyActivityDto accMoneyActivityDto) {

        validateAccMoneyActivityDto(accMoneyActivityDto);

        Long accountId = accMoneyActivityDto.getAccAccountId();
        BigDecimal amount = accMoneyActivityDto.getAmount();
        AccAccountActivityType activityType = accMoneyActivityDto.getActivityType();

        AccAccount accAccount = accAccountEntityService.getByIdWithControl(accountId);
        BigDecimal newBalance = accAccount.getCurrentBalance().add(amount);

        AccAccountActivity accAccountActivity = createAccAccountActivity(accountId, amount, newBalance, activityType);

        updateCurrentBalance(accAccount, newBalance);

        return accAccountActivity;
    }

    private AccAccountActivity createAccAccountActivity(Long accountId, BigDecimal amount, BigDecimal newBalance, AccAccountActivityType accAccountActivityType) {
        AccAccountActivity accAccountActivity = new AccAccountActivity();
        accAccountActivity.setAccountActivityType(accAccountActivityType);
        accAccountActivity.setAccAccountId(accountId);
        accAccountActivity.setAmount(amount);
        accAccountActivity.setTransactionDate(new Date());
        accAccountActivity.setCurrentBalance(newBalance);
        accAccountActivity = accAccountActivityEntityService.save(accAccountActivity);
        return accAccountActivity;
    }

    private void updateCurrentBalance(AccAccount accAccount, BigDecimal newBalance) {
        accAccount.setCurrentBalance(newBalance);
        accAccount = accAccountEntityService.save(accAccount);
    }

    private void validateBalance(BigDecimal newBalance) {
        if (newBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new GenBusinessException(AccErrorMessage.INSUFFICIENT_BALANCE);
        }
    }

    private void validateAccMoneyActivityRequestDto(AccMoneyActivityRequestDto accMoneyActivityRequestDto) {
        if (accMoneyActivityRequestDto == null){
            throw new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }

    private void validateAccMoneyActivityDto(AccMoneyActivityDto accMoneyActivityDto) {

        if (accMoneyActivityDto == null){
            throw new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }
}
