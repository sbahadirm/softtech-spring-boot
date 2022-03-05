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
@Transactional
@RequiredArgsConstructor
public class TransactionalService {

    private final CusCustomerEntityService cusCustomerEntityService;
    private final NonTransactionalService nonTransactionalService;

    public void save(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts2");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    public void saveT2N(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts3");

        cusCustomerEntityService.save(cusCustomer);

        nonTransactionalService.save();

        System.out.println("end");
    }

    public void saveT2T(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts5");

        cusCustomerEntityService.save(cusCustomer);

        save();

        System.out.println("end");
    }

    public void saveButError(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts6");

        cusCustomerEntityService.save(cusCustomer);

//        try {
//            testmethod();
//        } catch (Exception e){
//            e.printStackTrace();
//        }

        System.out.println("end");
    }

//    private void testmethod() {
//        throw new RuntimeException("error");
//    }

    public void saveT2RN(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts8-1");

        cusCustomerEntityService.save(cusCustomer);

        saveRN();

        System.out.println("end");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRN(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts8-2");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }
}
