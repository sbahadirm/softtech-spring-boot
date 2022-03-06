package com.softtech.softtechspringboot.app.crd.service.entityservice;

import com.softtech.softtechspringboot.app.crd.dao.CrdCreditCardActivityDao;
import com.softtech.softtechspringboot.app.crd.entity.CrdCreditCardActivity;
import com.softtech.softtechspringboot.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@Transactional
public class CrdCreditCardActivityEntityService extends BaseEntityService<CrdCreditCardActivity, CrdCreditCardActivityDao> {

    public CrdCreditCardActivityEntityService(CrdCreditCardActivityDao dao) {
        super(dao);
    }

    public List<CrdCreditCardActivity> findAllByCrdCreditCardIdAndTransactionDateBetween(Long crdCreditCardId, Date startDate, Date endDate){
        return getDao().findAllByCrdCreditCardIdAndTransactionDateBetween(crdCreditCardId, startDate, endDate);
    }
}
