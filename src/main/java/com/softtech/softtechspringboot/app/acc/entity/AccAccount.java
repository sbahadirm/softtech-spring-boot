package com.softtech.softtechspringboot.app.acc.entity;

import com.softtech.softtechspringboot.app.acc.enums.AccAccountType;
import com.softtech.softtechspringboot.app.acc.enums.AccCurrencyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Entity
@Table(name = "ACC_ACCOUNT")
@Getter
@Setter
public class AccAccount {

    @Id
    @SequenceGenerator(name = "AccAccount" , sequenceName = "ACC_ACCOUNT_ID_SEQ")
    @GeneratedValue(generator = "AccAccount")
    private Long id;

    @Column(name = "ID_CUS_CUSTOMER")
    private Long cusCustomerId;

    @Column(name = "IBAN_NO", length = 30)
    private String ibanNo;

    @Column(name = "CURRENT_BALANCE", precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_TYPE", length = 30)
    private AccCurrencyType currencyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", length = 30)
    private AccAccountType accountType;

}
