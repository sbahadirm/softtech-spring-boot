package com.softtech.softtechspringboot.app.transactional.service;

import com.softtech.softtechspringboot.app.cus.entity.CusCustomer;
import com.softtech.softtechspringboot.app.cus.service.entityservice.CusCustomerEntityService;
import com.softtech.softtechspringboot.app.transactional.util.TransactionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class NonTransactionalService {

    private final CusCustomerEntityService cusCustomerEntityService;
    private TransactionalService transactionalService;
    private final TransactionalService2 transactionalService2;
    private final NonTransactionalConstantService nonTransactionalConstantService;

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

    public void saveN2M(){

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts11-N");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService.saveMandatory();

        System.out.println("end");
    }

    public void saveN2S() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts12-T");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService2.saveSupports();

        System.out.println("end");
    }

    public void doSomething(){

        for (int i = 0; i < 9999; i++){
            CusCustomer cusCustomer = nonTransactionalConstantService.findById(1L);
        }
    }

    public void saveNon2Never() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts20-Non");

        cusCustomerEntityService.save(cusCustomer);

        nonTransactionalConstantService.saveNever();

        System.out.println("end");
    }
}
