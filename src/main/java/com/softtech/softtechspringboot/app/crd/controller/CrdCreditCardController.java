package com.softtech.softtechspringboot.app.crd.controller;

import com.softtech.softtechspringboot.app.crd.dao.CrdCreditCardDao;
import com.softtech.softtechspringboot.app.crd.dto.CrdCreditCardResponseDto;
import com.softtech.softtechspringboot.app.crd.dto.CrdCreditCardSaveRequestDto;
import com.softtech.softtechspringboot.app.crd.entity.CrdCreditCard;
import com.softtech.softtechspringboot.app.crd.service.CrdCreditCardService;
import com.softtech.softtechspringboot.app.gen.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/credit-card")
@RequiredArgsConstructor
public class CrdCreditCardController {

    private final CrdCreditCardService crdCreditCardService;

    /**
     * findAll
     * findById
     * delete(maybe passive) (cancel)
     */

    @GetMapping
    public ResponseEntity findAll(){

        List<CrdCreditCardResponseDto> crdCreditCardResponseDtoList = crdCreditCardService.findAll();

        return ResponseEntity.ok(RestResponse.of(crdCreditCardResponseDtoList));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CrdCreditCardSaveRequestDto crdCreditCardSaveRequestDto){

        CrdCreditCardResponseDto crdCreditCardResponseDto = crdCreditCardService.saveCreditCard(crdCreditCardSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardResponseDto));
    }
}
