package com.softtech.softtechspringboot.app.transactional.service;

import com.softtech.softtechspringboot.app.cus.entity.CusCustomer;
import com.softtech.softtechspringboot.app.cus.service.entityservice.CusCustomerEntityService;
import com.softtech.softtechspringboot.app.transactional.util.TransactionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionalService2 {

    private final CusCustomerEntityService cusCustomerEntityService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRN(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts9-2");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRN(int i){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts10-" + i);

        cusCustomerEntityService.save(cusCustomer);

        if (i == 7){
            throw new RuntimeException("error");
        }

        System.out.println("end ->" + i);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(int i){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts10-" + i);

        cusCustomerEntityService.save(cusCustomer);

        if (i == 7){
            throw new RuntimeException("error");
        }

        System.out.println("end ->" + i);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void saveMandatory(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts12-M");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }
}
