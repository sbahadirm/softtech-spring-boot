package com.softtech.softtechspringboot.app.cus.dto;

import lombok.Data;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
public class CusCustomerDto {

    private Long id;
    private String name;
    private String surname;
    private Long identityNo;
}
