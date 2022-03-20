package com.softtech.softtechspringboot.app.acc.dto;

import com.softtech.softtechspringboot.app.acc.enums.AccAccountType;
import com.softtech.softtechspringboot.app.acc.enums.AccCurrencyType;
import com.softtech.softtechspringboot.app.gen.enums.GenStatusType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
public class AccAccountDto {

    private Long id;
    private Long cusCustomerId;
    private String ibanNo;
    private BigDecimal currentBalance;
    private AccCurrencyType currencyType;
    private AccAccountType accountType;
    private GenStatusType statusType;
}
