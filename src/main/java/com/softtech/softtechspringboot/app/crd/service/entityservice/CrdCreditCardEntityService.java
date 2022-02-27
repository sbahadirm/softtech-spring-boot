package com.softtech.softtechspringboot.app.crd.service.entityservice;

import com.softtech.softtechspringboot.app.crd.dao.CrdCreditCardDao;
import com.softtech.softtechspringboot.app.crd.dto.CrdCreditCardDetails;
import com.softtech.softtechspringboot.app.crd.entity.CrdCreditCard;
import com.softtech.softtechspringboot.app.gen.enums.GenStatusType;
import com.softtech.softtechspringboot.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
public class CrdCreditCardEntityService extends BaseEntityService<CrdCreditCard, CrdCreditCardDao> {

    public CrdCreditCardEntityService(CrdCreditCardDao dao) {
        super(dao);
    }

    public List<CrdCreditCard> findAllByStatusType(GenStatusType statusType){
        return getDao().findAllByStatusType(statusType);
    }

    public List<CrdCreditCard> findAllActiveCreditCardList(){
        return getDao().findAllByStatusType(GenStatusType.ACTIVE);
    }

    public CrdCreditCard findByCardNoAndCvvNoAndExpireDate(Long cardNo, Long cvvNo, Date expireDate){
        return getDao().findByCardNoAndCvvNoAndExpireDateAndStatusType(cardNo, cvvNo, expireDate, GenStatusType.ACTIVE);
    }

    public CrdCreditCardDetails getCreditCardDetails(Long creditCardId){
        return getDao().getCreditCardDetails(creditCardId);
    }
}
