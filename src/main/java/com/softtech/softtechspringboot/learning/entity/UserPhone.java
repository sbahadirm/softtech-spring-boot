package com.softtech.softtechspringboot.learning.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Entity
@Table(name = "USER_PHONE")
@Data
public class UserPhone {

    @Id
//    @SequenceGenerator(name = "UserPhone", sequenceName = "USER_PHONE_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String phoneNumber;

}
