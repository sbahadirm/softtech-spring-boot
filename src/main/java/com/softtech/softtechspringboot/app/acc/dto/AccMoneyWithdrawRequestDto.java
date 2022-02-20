package com.softtech.softtechspringboot.app.acc.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
public class AccMoneyWithdrawRequestDto {

    private Long accAccountId;
    private BigDecimal amount;

}
