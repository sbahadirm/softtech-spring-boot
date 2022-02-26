package com.softtech.softtechspringboot.app.crd.dto;

import com.softtech.softtechspringboot.app.crd.enums.CrdCreditCardActivityType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
public class CrdCreditCardActivityDto {

    private Long crdCreditCardId;
    private BigDecimal amount;
    private Date transactionDate;
    private String description;
    private CrdCreditCardActivityType cardActivityType;
}
