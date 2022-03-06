package com.softtech.softtechspringboot.app.sec.dto;

import lombok.Data;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
public class SecLoginRequestDto {

    private Long identityNo;
    private String password;
}
