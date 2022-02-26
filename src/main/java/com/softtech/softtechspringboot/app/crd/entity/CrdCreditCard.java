package com.softtech.softtechspringboot.app.crd.entity;

import com.softtech.softtechspringboot.app.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Entity
@Table(name = "CRD_CREDIT_CARD")
@Getter
@Setter
public class CrdCreditCard extends BaseEntity {

    @Id
    @SequenceGenerator(name = "CrdCreditCard", sequenceName = "CRD_CREDIT_CARD_ID_SEQ")
    @GeneratedValue(generator = "CrdCreditCard")
    private Long id;

    @Column(name = "ID_CUS_CUSTOMER", nullable = false)
    private Long cusCustomerId;

    @Column(name = "CARD_NO", nullable = false)
    private Long cardNo;

    @Column(name = "CVV_NO", nullable = false)
    private Long cvvNo;

    @Column(name = "EXPIRE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expireDate;

    @Column(name = "TOTAL_LIMIT", precision = 19, scale = 2)
    private BigDecimal totalLimit;

    @Column(name = "AVAILABLE_CARD_LIMIT", precision = 19, scale = 2)
    private BigDecimal availableCardLimit;

    @Column(name = "CURRENT_DEBT", precision = 19, scale = 2)
    private BigDecimal currentDebt;

    @Column(name = "MINIMUM_PAYMENT_AMOUNT", precision = 19, scale = 2)
    private BigDecimal minimumPaymentAmount;

    @Column(name = "CUTOFF_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date cutoffDate;

    @Column(name = "DUE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dueDate;
}
