package com.softtech.softtechspringboot.app.crd.dao;

import com.softtech.softtechspringboot.app.crd.entity.CrdCreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Repository
public interface CrdCreditCardDao extends JpaRepository<CrdCreditCard, Long> {
}
