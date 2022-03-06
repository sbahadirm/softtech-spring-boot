package com.softtech.softtechspringboot.app.acc.service;

import com.softtech.softtechspringboot.app.acc.converter.AccAccountMapper;
import com.softtech.softtechspringboot.app.acc.dto.AccAccountDto;
import com.softtech.softtechspringboot.app.acc.dto.AccAccountSaveRequestDto;
import com.softtech.softtechspringboot.app.acc.entity.AccAccount;
import com.softtech.softtechspringboot.app.acc.service.entityservice.AccAccountEntityService;
import com.softtech.softtechspringboot.app.gen.enums.GenStatusType;
import com.softtech.softtechspringboot.app.gen.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AccAccountService {

    private final AccAccountEntityService accAccountEntityService;

    public List<AccAccountDto> findAll() {

        List<AccAccount> accAccountList = accAccountEntityService.findAllActiveAccAccountList();

        List<AccAccountDto> accAccountDtoList = AccAccountMapper.INSTANCE.convertToAccAccountDtoList(accAccountList);

        return accAccountDtoList;
    }

    public AccAccountDto findById(Long id) {

        AccAccount accAccount = accAccountEntityService.getByIdWithControl(id);

        AccAccountDto accAccountDto = AccAccountMapper.INSTANCE.convertToAccAccountDto(accAccount);

        return accAccountDto;
    }

    public AccAccountDto save(AccAccountSaveRequestDto accAccountSaveRequestDto) {

        String ibanNo = getIbanNo();

        Long currentCustomerId = accAccountEntityService.getCurrentCustomerId();

        AccAccount accAccount = AccAccountMapper.INSTANCE.convertToAccAccount(accAccountSaveRequestDto);
        accAccount.setStatusType(GenStatusType.ACTIVE);
        accAccount.setIbanNo(ibanNo);
        accAccount.setCusCustomerId(currentCustomerId);

        accAccount = accAccountEntityService.save(accAccount);

        AccAccountDto accAccountDto = AccAccountMapper.INSTANCE.convertToAccAccountDto(accAccount);

        return accAccountDto;
    }

    public void cancel(Long accountId) {

        AccAccount accAccount = accAccountEntityService.getByIdWithControl(accountId);
        accAccount.setStatusType(GenStatusType.PASSIVE);
        accAccount.setCancelDate(new Date());

        accAccountEntityService.save(accAccount);
    }

    private String getIbanNo() {
        String ibanNo = StringUtil.getRandomNumberAsString(26);
        return ibanNo;
    }
}
