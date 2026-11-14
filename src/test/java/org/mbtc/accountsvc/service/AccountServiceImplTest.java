package org.mbtc.accountsvc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mbtc.accountsvc.domain.Account;
import org.mbtc.accountsvc.domain.AccountSaving;
import org.mbtc.accountsvc.dto.AccountReq;
import org.mbtc.accountsvc.dto.AccountResp;
import org.mbtc.accountsvc.dto.AccountSavingReq;
import org.mbtc.accountsvc.dto.AccountSavingResp;
import org.mbtc.accountsvc.enums.AccountType;
import org.mbtc.accountsvc.exception.CustomerNotFoundException;
import org.mbtc.accountsvc.repository.AccountRepository;
import org.mbtc.accountsvc.util.RespMessageUtil;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createCustomerAccount_savesAccountWithItsSaving() {
        when(accountRepository.save(any(Account.class)))
                .thenReturn(Account.builder().id(42L).build());

        Long customerNumber = accountService.createCustomerAccount(accountRequest());

        assertThat(customerNumber).isEqualTo(42L);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());

        Account savedAccount = accountCaptor.getValue();
        assertThat(savedAccount.getCustomerName()).isEqualTo("Jane Doe");
        assertThat(savedAccount.getCustomerMobile()).isEqualTo("09171234567");
        assertThat(savedAccount.getCustomerEmail()).isEqualTo("jane.doe@example.com");
        assertThat(savedAccount.getAddressLineOne()).isEqualTo("1 Test Street");
        assertThat(savedAccount.getAddressLineTwo()).isEqualTo("Unit 2");
        assertThat(savedAccount.getAccountSavings()).hasSize(1);

        AccountSaving savedSaving = savedAccount.getAccountSavings().get(0);
        assertThat(savedSaving.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(savedSaving.getAvailableBalance()).isEqualByComparingTo("1500.75");
        assertThat(savedSaving.getAccount()).isSameAs(savedAccount);
    }

    @Test
    void getCustomerAccount_returnsCustomerWithSavings() {
        Account account = Account.builder()
                .id(1L)
                .customerName("John Doe")
                .build();
        account.addAccountSaving(
                AccountSaving.builder()
                        .id(7L)
                        .accountType(AccountType.CHECKING)
                        .availableBalance(new BigDecimal("250.50"))
                        .build()
        );
        when(accountRepository.findAccountByIdWithSavings(1L)).thenReturn(Optional.of(account));

        AccountResp response = accountService.getCustomerAccount(1L);

        assertThat(response.getCustomerNumber()).isEqualTo(1L);
        assertThat(response.getTransactionStatusCode()).isEqualTo(302);
        assertThat(response.getTransactionStatusDescription()).isEqualTo(RespMessageUtil.CUSTOMER_FOUND);
        assertThat(response.getSavings()).hasSize(1);

        AccountSavingResp saving = response.getSavings().get(0);
        assertThat(saving.getAccountNumber()).isEqualTo(7L);
        assertThat(saving.getAccountType()).isEqualTo(AccountType.CHECKING);
        assertThat(saving.getAvailableBalance()).isEqualByComparingTo("250.50");
    }

    @Test
    void getCustomerAccount_returnsEmptySavingsWhenAccountHasNone() {
        when(accountRepository.findAccountByIdWithSavings(3L))
                .thenReturn(Optional.of(Account.builder().id(3L).build()));

        AccountResp response = accountService.getCustomerAccount(3L);

        assertThat(response.getCustomerNumber()).isEqualTo(3L);
        assertThat(response.getSavings()).isEmpty();
    }

    @Test
    void getCustomerAccount_throwsWhenCustomerDoesNotExist() {
        when(accountRepository.findAccountByIdWithSavings(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getCustomerAccount(999L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(RespMessageUtil.CUSTOMER_NOT_FOUND);
    }

    private AccountReq accountRequest() {
        AccountSavingReq savingReq = new AccountSavingReq();
        savingReq.setAccountType(AccountType.SAVINGS);
        savingReq.setAvailableBalance(new BigDecimal("1500.75"));

        AccountReq request = new AccountReq();
        request.setCustomerName("Jane Doe");
        request.setCustomerMobile("09171234567");
        request.setCustomerEmail("jane.doe@example.com");
        request.setAddressLineOne("1 Test Street");
        request.setAddressLineTwo("Unit 2");
        request.setAccountSavingReq(savingReq);
        return request;
    }
}
