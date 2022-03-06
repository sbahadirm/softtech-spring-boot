package com.softtech.softtechspringboot.app.acc.service.entityservice;

import com.softtech.softtechspringboot.app.acc.dao.AccAccountActivityDao;
import com.softtech.softtechspringboot.app.acc.entity.AccAccountActivity;
import com.softtech.softtechspringboot.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@Transactional
public class AccAccountActivityEntityService extends BaseEntityService<AccAccountActivity, AccAccountActivityDao> {

    public AccAccountActivityEntityService(AccAccountActivityDao dao) {
        super(dao);
    }
}
