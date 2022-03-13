package com.softtech.softtechspringboot.app.acc.service;

import com.softtech.softtechspringboot.app.acc.converter.AccAccountMapper;
import com.softtech.softtechspringboot.app.acc.dto.AccMoneyActivityDto;
import com.softtech.softtechspringboot.app.acc.dto.AccMoneyTransferDto;
import com.softtech.softtechspringboot.app.acc.dto.AccMoneyTransferSaveRequestDto;
import com.softtech.softtechspringboot.app.acc.entity.AccAccountActivity;
import com.softtech.softtechspringboot.app.acc.entity.AccMoneyTransfer;
import com.softtech.softtechspringboot.app.acc.enums.AccAccountActivityType;
import com.softtech.softtechspringboot.app.acc.service.entityservice.AccMoneyTransferEntityService;
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
public class AccMoneyTransferService {

    private final AccMoneyTransferEntityService accMoneyTransferEntityService;
    private final AccAccountActivityService accAccountActivityService;

    public AccMoneyTransferDto transferMoney(AccMoneyTransferSaveRequestDto accMoneyTransferSaveRequestDto){

        AccMoneyTransfer accMoneyTransfer = AccAccountMapper.INSTANCE.convertToAccMoneyTransfer(accMoneyTransferSaveRequestDto);
        accMoneyTransfer.setTransferDate(new Date());

        Long accountIdFrom = accMoneyTransfer.getAccountIdFrom();
        Long accountIdTo = accMoneyTransfer.getAccountIdTo();
        BigDecimal amount = accMoneyTransfer.getAmount();

        AccMoneyActivityDto accMoneyActivityDtoOut = AccMoneyActivityDto.builder()
                .accAccountId(accountIdFrom)
                .amount(amount)
                .activityType(AccAccountActivityType.SEND)
                .build();

        AccMoneyActivityDto accMoneyActivityDtoIn = AccMoneyActivityDto.builder()
                .accAccountId(accountIdTo)
                .amount(amount)
                .activityType(AccAccountActivityType.GET)
                .build();

        accAccountActivityService.moneyOut(accMoneyActivityDtoOut);
        accAccountActivityService.moneyIn(accMoneyActivityDtoIn);

        accMoneyTransfer = accMoneyTransferEntityService.save(accMoneyTransfer);
        AccMoneyTransferDto accMoneyTransferDto = AccAccountMapper.INSTANCE.convertToAccMoneyTransferDto(accMoneyTransfer);

        return accMoneyTransferDto;
    }
}
