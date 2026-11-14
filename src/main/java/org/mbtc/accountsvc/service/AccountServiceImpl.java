package org.mbtc.accountsvc.service;

import lombok.RequiredArgsConstructor;
import org.mbtc.accountsvc.domain.Account;
import org.mbtc.accountsvc.domain.AccountSaving;
import org.mbtc.accountsvc.dto.AccountReq;
import org.mbtc.accountsvc.dto.AccountResp;
import org.mbtc.accountsvc.dto.AccountSavingReq;
import org.mbtc.accountsvc.dto.AccountSavingResp;
import org.mbtc.accountsvc.exception.CustomerNotFoundException;
import org.mbtc.accountsvc.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;
import static org.mbtc.accountsvc.util.RespMessageUtil.CUSTOMER_FOUND;
import static org.mbtc.accountsvc.util.RespMessageUtil.CUSTOMER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Override
    public Long createCustomerAccount(AccountReq accountReq) {

        AccountSavingReq accountSavingReq = accountReq.getAccountSavingReq();

        Account newAccount = Account.builder()
                .customerName(accountReq.getCustomerName())
                .customerMobile(accountReq.getCustomerMobile())
                .customerEmail(accountReq.getCustomerEmail())
                .addressLineOne(accountReq.getAddressLineOne())
                .addressLineTwo(accountReq.getAddressLineTwo())
                .build();

        newAccount.addAccountSaving(
                AccountSaving.builder()
                        .accountType(accountSavingReq.getAccountType())
                        .availableBalance(accountSavingReq.getAvailableBalance())
                        .build()
        );

        Account account = accountRepository.save(newAccount);

        return account.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResp getCustomerAccount(Long customerNumber) {

        Account account = accountRepository.findAccountByIdWithSavings(customerNumber)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));

        return new AccountResp(
                account.getId(),
                HttpStatus.FOUND.value(),
                CUSTOMER_FOUND,
                account.getAccountSavings()
                        .stream().map(item -> new AccountSavingResp(
                                item.getId(),
                                item.getAccountType(),
                                item.getAvailableBalance()
                            )
                        ).collect(Collectors.toList())

        );
    }
}
