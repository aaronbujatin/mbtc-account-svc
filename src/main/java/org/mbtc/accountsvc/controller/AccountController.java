package org.mbtc.accountsvc.controller;

import lombok.RequiredArgsConstructor;
import org.mbtc.accountsvc.dto.AccountReq;
import org.mbtc.accountsvc.dto.AccountResp;
import org.mbtc.accountsvc.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/accounts")
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    //can be use ResponseEntity to configure response
    @PostMapping
    public AccountResp createCustomerAccount(
            @Valid @RequestBody AccountReq accountReq) {

        Long id = accountService.createCustomerAccount(accountReq);

        return new AccountResp(
                id,
                HttpStatus.CREATED.value(),
                "Customer Account Created",
                null
        );

    }

    @GetMapping("/{customerNumber}")
    public AccountResp getCustomerAccount(@PathVariable Long customerNumber) {
        return accountService.getCustomerAccount(customerNumber);
    }



}

