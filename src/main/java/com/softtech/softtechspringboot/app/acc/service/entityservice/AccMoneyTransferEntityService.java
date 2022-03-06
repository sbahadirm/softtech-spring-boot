package com.softtech.softtechspringboot.app.acc.service.entityservice;

import com.softtech.softtechspringboot.app.acc.dao.AccMoneyTransferDao;
import com.softtech.softtechspringboot.app.acc.entity.AccMoneyTransfer;
import com.softtech.softtechspringboot.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@Transactional
public class AccMoneyTransferEntityService extends BaseEntityService<AccMoneyTransfer, AccMoneyTransferDao> {

    public AccMoneyTransferEntityService(AccMoneyTransferDao dao) {
        super(dao);
    }
}
