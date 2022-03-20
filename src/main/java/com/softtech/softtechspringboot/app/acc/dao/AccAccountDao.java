package com.softtech.softtechspringboot.app.acc.dao;

import com.softtech.softtechspringboot.app.acc.entity.AccAccount;
import com.softtech.softtechspringboot.app.gen.enums.GenStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Repository
public interface AccAccountDao extends JpaRepository<AccAccount, Long> {

    List<AccAccount> findAllByStatusType(GenStatusType statusType);

    Page<AccAccount> findAllByStatusType(GenStatusType statusType, Pageable pageable);
}
