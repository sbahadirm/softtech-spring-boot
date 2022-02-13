package com.softtech.softtechspringboot.learning.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
@Builder
public class UserDtoWithLombok {

    private Long id;
    private String name;
    private String surname;
    private String password;
    private String role;
    private String email;

}
