package com.softtech.softtechspringboot.app.transactional.service;

import com.softtech.softtechspringboot.app.cus.entity.CusCustomer;
import com.softtech.softtechspringboot.app.cus.service.entityservice.CusCustomerEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class NonTransactionalService {

    private final CusCustomerEntityService cusCustomerEntityService;

    public void save(){

        CusCustomer cusCustomer = new CusCustomer();
        cusCustomer.setName("test");
        cusCustomer.setSurname("test");
        cusCustomer.setIdentityNo(12312312312L);
        cusCustomer.setPassword("123");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }
}
