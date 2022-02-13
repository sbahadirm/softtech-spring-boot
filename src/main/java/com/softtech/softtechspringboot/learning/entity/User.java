package com.softtech.softtechspringboot.learning.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Entity
@Table(name = "USER")
@Data
public class User {

    @Id
//    @SequenceGenerator(name = "User", sequenceName = "USER_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
