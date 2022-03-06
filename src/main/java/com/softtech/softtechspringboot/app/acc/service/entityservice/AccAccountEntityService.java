package com.softtech.softtechspringboot.app.acc.service.entityservice;

import com.softtech.softtechspringboot.app.acc.dao.AccAccountDao;
import com.softtech.softtechspringboot.app.acc.entity.AccAccount;
import com.softtech.softtechspringboot.app.gen.enums.GenStatusType;
import com.softtech.softtechspringboot.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@Transactional
public class AccAccountEntityService extends BaseEntityService<AccAccount, AccAccountDao> {

    public AccAccountEntityService(AccAccountDao dao) {
        super(dao);
    }

    public List<AccAccount> findAllActiveAccAccountList(){
        return getDao().findAllByStatusType(GenStatusType.ACTIVE);
    }

    public List<AccAccount> findAllByStatusType(GenStatusType statusType){
        return getDao().findAllByStatusType(statusType);
    }
}
