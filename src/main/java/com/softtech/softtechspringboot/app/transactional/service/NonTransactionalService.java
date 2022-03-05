package com.softtech.softtechspringboot.app.transactional.service;

import com.softtech.softtechspringboot.app.cus.entity.CusCustomer;
import com.softtech.softtechspringboot.app.cus.service.entityservice.CusCustomerEntityService;
import com.softtech.softtechspringboot.app.transactional.util.TransactionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class NonTransactionalService {

    private final CusCustomerEntityService cusCustomerEntityService;
    private TransactionalService transactionalService;

    @Autowired
    public void setTransactionalService(@Lazy TransactionalService transactionalService) {
        this.transactionalService = transactionalService;
    }

    public void save(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts1");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    public void saveN2T(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts4");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService.save();

        System.out.println("end");
    }

    public void saveButError(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts7");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");

        throw new RuntimeException("error");

//        try {
//            testmethod();
//        } catch (Exception e){
//            e.printStackTrace();
//        }

    }

//    private void testmethod() {
//        throw new RuntimeException("error");
//    }
}
