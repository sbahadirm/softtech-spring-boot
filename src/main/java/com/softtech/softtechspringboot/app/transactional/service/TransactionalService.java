package com.softtech.softtechspringboot.app.transactional.service;

import com.softtech.softtechspringboot.app.cus.entity.CusCustomer;
import com.softtech.softtechspringboot.app.cus.service.entityservice.CusCustomerEntityService;
import com.softtech.softtechspringboot.app.transactional.util.TransactionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
