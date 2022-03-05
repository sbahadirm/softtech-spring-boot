package com.softtech.softtechspringboot.app.transactional.service;

import com.softtech.softtechspringboot.app.cus.dao.CusCustomerDao;
import com.softtech.softtechspringboot.app.cus.entity.CusCustomer;
import com.softtech.softtechspringboot.app.transactional.util.TransactionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class NonTransactionalConstantService {

    private final CusCustomerDao cusCustomerDao;

    private Map<Long, CusCustomer> map = new LinkedHashMap<>();

    public CusCustomer findById(Long id){

        CusCustomer customer = map.get(id);
        if (customer != null){
            return customer;
        }

        Optional<CusCustomer> customerOptional = cusCustomerDao.findById(id);

        CusCustomer cusCustomer;
        if (customerOptional.isPresent()){
            cusCustomer = customerOptional.get();
        } else {
            throw new RuntimeException("Error");
        }

        map.put(cusCustomer.getId(), cusCustomer);

        return cusCustomer;
    }

    @Transactional(propagation = Propagation.NEVER)
    public void saveNever() {

        CusCustomer cusCustomer = TransactionalUtil.getDummyCusCustomer("ts20-N");

        cusCustomerDao.save(cusCustomer);

        System.out.println("end");
    }
}
