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
    private final TransactionalService2 transactionalService2;

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

    /**
     * https://stackoverflow.com/questions/28480480/propagation-requires-new-does-not-create-a-new-transaction-in-spring-with-jpa
     *
     * Spring transactions are proxy-based. Here's thus how it works when bean A causes a transactional
     * of bean B. A has in fact a reference to a proxy, which delegates to the bean B.
     * This proxy is the one which starts and commits/rollbacks the transaction:
     *
     * A ---> proxy ---> B
     * In your code, a transactional method of A calls another transactional method of A.
     * So Spring can't intercept the call and start a new transaction.
     * It's a regular method call without any proxy involved.
     *
     * So, if you want a new transaction to start, the method createSampleObject() should be in another bean,
     * injected into your current bean.
     */
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

    public void saveT2RNWithDifferentBean(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts9-1");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService2.saveRN();

        System.out.println("end");
    }

    public void saveT2RNButError(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts10");

        cusCustomerEntityService.save(cusCustomer);

        for (int i = 0; i < 10; i++){
            try {
                transactionalService2.saveRN(i);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        System.out.println("end");
    }

    public void saveT2TButError(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts10");

        cusCustomerEntityService.save(cusCustomer);

        for (int i = 0; i < 10; i++){
            try {
                transactionalService2.save(i);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        System.out.println("end");
    }
}
