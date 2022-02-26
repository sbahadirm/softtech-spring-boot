package com.softtech.softtechspringboot.app.crd.controller;

import com.softtech.softtechspringboot.app.crd.dto.CrdCreditCardActivityDto;
import com.softtech.softtechspringboot.app.crd.dto.CrdCreditCardResponseDto;
import com.softtech.softtechspringboot.app.crd.dto.CrdCreditCardSaveRequestDto;
import com.softtech.softtechspringboot.app.crd.dto.CrdCreditCardSpendDto;
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

    @PostMapping
    public ResponseEntity save(@RequestBody CrdCreditCardSaveRequestDto crdCreditCardSaveRequestDto){

        CrdCreditCardResponseDto crdCreditCardResponseDto = crdCreditCardService.saveCreditCard(crdCreditCardSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardResponseDto));
    }

    @GetMapping
    public ResponseEntity findAll(){

        List<CrdCreditCardResponseDto> crdCreditCardResponseDtoList = crdCreditCardService.findAll();

        return ResponseEntity.ok(RestResponse.of(crdCreditCardResponseDtoList));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){

        CrdCreditCardResponseDto crdCreditCardResponseDto = crdCreditCardService.findById(id);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardResponseDto));
    }

    @PatchMapping("/cancel/{cardId}")
    public ResponseEntity cancel(@PathVariable Long cardId){

        crdCreditCardService.cancel(cardId);

        return ResponseEntity.ok(RestResponse.empty());
    }

    /**
     * spend
     * refund
     * payment
     * ?limit increase demand
     * findAllActivitiesBetweenDates
     * statementOfAccount
     */

    @PostMapping("/spend")
    public ResponseEntity spend(@RequestBody CrdCreditCardSpendDto crdCreditCardSpendDto){

        CrdCreditCardActivityDto crdCreditCardActivityDto = crdCreditCardService.spend(crdCreditCardSpendDto);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDto));
    }

    @PostMapping("/refund/{activityId}")
    public ResponseEntity refund(@PathVariable Long activityId){

        CrdCreditCardActivityDto crdCreditCardActivityDto = crdCreditCardService.refund(activityId);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDto));
    }
}
