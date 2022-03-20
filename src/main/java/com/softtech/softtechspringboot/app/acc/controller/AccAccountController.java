package com.softtech.softtechspringboot.app.acc.controller;

import com.softtech.softtechspringboot.app.acc.dto.*;
import com.softtech.softtechspringboot.app.acc.service.AccAccountActivityService;
import com.softtech.softtechspringboot.app.acc.service.AccAccountService;
import com.softtech.softtechspringboot.app.acc.service.AccMoneyTransferService;
import com.softtech.softtechspringboot.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @Operation(tags = "Account Controller")
    @GetMapping
    public ResponseEntity getAll(
            Optional<Integer> pageOptional,
            Optional<Integer> sizeOptional
    ){

        List<AccAccountDto> accAccountDtoList = accAccountService.findAll(pageOptional, sizeOptional);

        return ResponseEntity.ok(RestResponse.of(accAccountDtoList));
    }

    @Operation(tags = "Account Controller")
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){

        AccAccountDto accAccountDto = accAccountService.findById(id);

        return ResponseEntity.ok(RestResponse.of(accAccountDto));
    }

    @Operation(tags = "Account Controller")
    @PostMapping
    public ResponseEntity save(@RequestBody AccAccountSaveRequestDto accAccountSaveRequestDto){

        AccAccountDto accAccountDto = accAccountService.save(accAccountSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(accAccountDto));
    }

    @Operation(tags = "Account Controller")
    @PatchMapping("/cancel/{accountId}")
    public ResponseEntity cancel(@PathVariable Long accountId){

        accAccountService.cancel(accountId);

        return ResponseEntity.ok(RestResponse.empty());
    }

    @Operation(tags = "Account Controller")
    @PostMapping("/money-transfer")
    public ResponseEntity transferMoney(@RequestBody AccMoneyTransferSaveRequestDto accMoneyTransferSaveRequestDto){

        AccMoneyTransferDto accMoneyTransferDto = accMoneyTransferService.transferMoney(accMoneyTransferSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(accMoneyTransferDto));

    }

    @Operation(tags = "Account Controller")
    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@RequestBody AccMoneyActivityRequestDto accMoneyActivityRequestDto){

        AccAccountActivityDto accAccountActivityDto = accAccountActivityService.withdraw(accMoneyActivityRequestDto);

        return ResponseEntity.ok(RestResponse.of(accAccountActivityDto));
    }

    @Operation(tags = "Account Controller")
    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestBody AccMoneyActivityRequestDto accMoneyActivityRequestDto){

        AccAccountActivityDto accAccountActivityDto = accAccountActivityService.deposit(accMoneyActivityRequestDto);

        return ResponseEntity.ok(RestResponse.of(accAccountActivityDto));
    }
}
