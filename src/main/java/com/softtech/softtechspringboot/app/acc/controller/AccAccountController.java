package com.softtech.softtechspringboot.app.acc.controller;

import com.softtech.softtechspringboot.app.acc.dto.*;
import com.softtech.softtechspringboot.app.acc.entity.AccAccount;
import com.softtech.softtechspringboot.app.acc.service.AccAccountActivityService;
import com.softtech.softtechspringboot.app.acc.service.AccAccountService;
import com.softtech.softtechspringboot.app.acc.service.AccMoneyTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccAccountController {

    private final AccAccountService accAccountService;
    private final AccMoneyTransferService accMoneyTransferService;
    private final AccAccountActivityService accAccountActivityService;

    @GetMapping
    public ResponseEntity getAll(){

        List<AccAccountDto> accAccountDtoList = accAccountService.findAll();

        return ResponseEntity.ok(accAccountDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){

        AccAccountDto accAccountDto = accAccountService.findById(id);

        return ResponseEntity.ok(accAccountDto);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody AccAccountSaveRequestDto accAccountSaveRequestDto){

        AccAccountDto accAccountDto = accAccountService.save(accAccountSaveRequestDto);

        return ResponseEntity.ok(accAccountDto);
    }

    @PatchMapping("/cancel/{accountId}")
    public ResponseEntity cancel(@PathVariable Long accountId){

        accAccountService.cancel(accountId);

        return ResponseEntity.ok(Void.TYPE);
    }

    @PostMapping("/money-transfer")
    public ResponseEntity transferMoney(@RequestBody AccMoneyTransferSaveRequestDto accMoneyTransferSaveRequestDto){

        AccMoneyTransferDto accMoneyTransferDto = accMoneyTransferService.transferMoney(accMoneyTransferSaveRequestDto);

        return ResponseEntity.ok(accMoneyTransferDto);

    }

    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@RequestBody AccMoneyWithdrawRequestDto accMoneyWithdrawRequestDto){

        AccAccountActivityDto accAccountActivityDto = accAccountActivityService.withdraw(accMoneyWithdrawRequestDto);

        return ResponseEntity.ok(accAccountActivityDto);
    }
}
