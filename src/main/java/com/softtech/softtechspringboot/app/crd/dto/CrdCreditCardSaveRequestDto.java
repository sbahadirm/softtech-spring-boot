package com.softtech.softtechspringboot.app.crd.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
public class CrdCreditCardSaveRequestDto {

    @NotNull
    private Long cusCustomerId;

    @NotNull
    private BigDecimal earning;

    private String cutoffDay;

}
