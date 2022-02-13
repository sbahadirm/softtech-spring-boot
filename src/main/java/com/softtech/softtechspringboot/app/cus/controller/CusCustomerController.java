package com.softtech.softtechspringboot.app.cus.controller;

import com.softtech.softtechspringboot.app.cus.dto.CusCustomerDto;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerSaveRequestDto;
import com.softtech.softtechspringboot.app.cus.entity.CusCustomer;
import com.softtech.softtechspringboot.app.cus.service.CusCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CusCustomerController {

    private final CusCustomerService cusCustomerService;

    @GetMapping
    public ResponseEntity findAll(){

        List<CusCustomerDto> cusCustomerDtoList = cusCustomerService.findAll();

        return new ResponseEntity(cusCustomerDtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CusCustomerSaveRequestDto cusCustomerSaveRequestDto){

        CusCustomerDto cusCustomerDto = cusCustomerService.save(cusCustomerSaveRequestDto);

        return new ResponseEntity(cusCustomerDto, HttpStatus.OK);
    }
}
