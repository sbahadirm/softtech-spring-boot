package com.softtech.softtechspringboot.app.acc.entity;

import com.softtech.softtechspringboot.app.acc.enums.AccMoneyTransferType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Entity
@Table(name = "ACC_MONEY_TRANSFER")
@Setter
@Getter
public class AccMoneyTransfer {

    @Id
    @GeneratedValue(generator = "AccMoneyTransfer")
    @SequenceGenerator(name = "AccMoneyTransfer", sequenceName = "ACC_MONEY_TRANSFER_ID_SEQ")
    private Long id;

    @Column(name = "ID_ACC_ACCOUNT_FROM")
    private Long accountIdFrom;

    @Column(name = "ID_ACC_ACCOUNT_TO")
    private Long accountIdTo;

    @Column(name = "AMOUNT", precision = 19, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSFER_DATE")
    private Date transferDate;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSFER_TYPE", length = 30)
    private AccMoneyTransferType transferType;

}
