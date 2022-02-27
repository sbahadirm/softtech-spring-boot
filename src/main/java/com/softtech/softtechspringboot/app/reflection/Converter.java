package com.softtech.softtechspringboot.app.reflection;

import com.softtech.softtechspringboot.app.acc.dto.AccAccountDto;
import com.softtech.softtechspringboot.app.acc.entity.AccAccount;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public class Converter {

    public static void main(String[] args) {

        Customer customer = getCustomer();

        CustomerDto customerDto = convertToCustomerDto(customer);

        System.out.println(customerDto);
    }

    private static CustomerDto convertToCustomerDto(Customer source) {
        CustomerDto target = new CustomerDto();
        target.setName(source.getName());
        target.setId(source.getId());
        target.setSurname(source.getSurname());
        return target;
    }

    public static AccAccountDto convertToAccAccountDto(AccAccount source){

        AccAccountDto target = new AccAccountDto();
        target.setStatusType(source.getStatusType());
        target.setIbanNo(source.getIbanNo());
        target.setCusCustomerId(source.getCusCustomerId());
        target.setAccountType(source.getAccountType());
        target.setCurrentBalance(source.getCurrentBalance());
        target.setCurrencyType(source.getCurrencyType());

        return target;
    }

    private static Customer getCustomer() {
        Customer customer = new Customer();
        customer.setName("bahadir");
        customer.setSurname("memiş");
        customer.setId(1L);
        return customer;
    }
}
