package com.softtech.softtechspringboot.app.cus.service;

import com.softtech.softtechspringboot.app.cus.converter.CusCustomerConverter;
import com.softtech.softtechspringboot.app.cus.converter.CusCustomerMapper;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerDto;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerSaveRequestDto;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerUpdateRequestDto;
import com.softtech.softtechspringboot.app.cus.entity.CusCustomer;
import com.softtech.softtechspringboot.app.cus.enums.CusErrorMessage;
import com.softtech.softtechspringboot.app.cus.service.entityservice.CusCustomerEntityService;
import com.softtech.softtechspringboot.app.gen.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CusCustomerService {

    private final CusCustomerEntityService cusCustomerEntityService;
    private final CusCustomerConverter cusCustomerConverter;
    private final PasswordEncoder passwordEncoder;

    public List<CusCustomerDto> findAll() {

        List<CusCustomer> cusCustomerList = cusCustomerEntityService.findAll();

        List<CusCustomerDto> cusCustomerDtoList = cusCustomerConverter.convertToCusCustomerDtoList(cusCustomerList);

        return cusCustomerDtoList;
    }

    public CusCustomerDto save(CusCustomerSaveRequestDto cusCustomerSaveRequestDto) {

        CusCustomer cusCustomer = CusCustomerMapper.INSTANCE.convertToCusCustomer(cusCustomerSaveRequestDto);

        String password = passwordEncoder.encode(cusCustomer.getPassword());
        cusCustomer.setPassword(password);

        cusCustomer = cusCustomerEntityService.save(cusCustomer);

        CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.convertToCusCustomerDto(cusCustomer);

        return cusCustomerDto;
    }


    public void delete(Long id) {

        CusCustomer cusCustomer = cusCustomerEntityService.getByIdWithControl(id);

        cusCustomerEntityService.delete(cusCustomer);
    }

    public CusCustomerDto findById(Long id) {

        CusCustomer cusCustomer = cusCustomerEntityService.getByIdWithControl(id);

        CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.convertToCusCustomerDto(cusCustomer);

        return cusCustomerDto;
    }


    public CusCustomerDto update(CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto) {

        controlIsCustomerExist(cusCustomerUpdateRequestDto);

        CusCustomer cusCustomer = CusCustomerMapper.INSTANCE.convertToCusCustomer(cusCustomerUpdateRequestDto);
        cusCustomer = cusCustomerEntityService.save(cusCustomer);

        CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.convertToCusCustomerDto(cusCustomer);

        return cusCustomerDto;
    }

    private void controlIsCustomerExist(CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto) {

        Long id = cusCustomerUpdateRequestDto.getId();
        boolean isExist = cusCustomerEntityService.existsById(id);
        if (!isExist){
            throw new ItemNotFoundException(CusErrorMessage.CUSTOMER_ERROR_MESSAGE);
        }
    }
}
