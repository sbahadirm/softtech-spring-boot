package com.softtech.softtechspringboot.app.acc.dto;

import com.softtech.softtechspringboot.app.acc.enums.AccAccountActivityType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
@Builder
public class AccMoneyActivityDto {

    private Long accAccountId;
    private BigDecimal amount;
    private AccAccountActivityType activityType;

}
