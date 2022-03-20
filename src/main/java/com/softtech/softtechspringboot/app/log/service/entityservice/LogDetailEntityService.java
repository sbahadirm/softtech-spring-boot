package com.softtech.softtechspringboot.app.log.service.entityservice;

import com.softtech.softtechspringboot.app.gen.service.BaseEntityService;
import com.softtech.softtechspringboot.app.log.dao.LogDetailsDao;
import com.softtech.softtechspringboot.app.log.entity.LogDetail;
import org.springframework.stereotype.Service;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
public class LogDetailEntityService extends BaseEntityService<LogDetail, LogDetailsDao> {

    public LogDetailEntityService(LogDetailsDao dao) {
        super(dao);
    }
}
