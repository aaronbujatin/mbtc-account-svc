package org.mbtc.accountsvc.repository;

import org.junit.jupiter.api.Test;
import org.mbtc.accountsvc.domain.Account;
import org.mbtc.accountsvc.domain.AccountSaving;
import org.mbtc.accountsvc.enums.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void savesAccountAndSavingTogetherAndFetchesThem() {
        Account account = Account.builder()
                .customerName("Jane Doe")
                .customerMobile("09171234567")
                .customerEmail("jane.doe@example.com")
                .addressLineOne("1 Test Street")
                .addressLineTwo("Unit 2")
                .build();
        account.addAccountSaving(
                AccountSaving.builder()
                        .accountType(AccountType.SAVINGS)
                        .availableBalance(new BigDecimal("75.25"))
                        .build()
        );

        Account savedAccount = accountRepository.saveAndFlush(account);
        entityManager.clear();

        Optional<Account> result = accountRepository.findAccountByIdWithSavings(savedAccount.getId());

        assertThat(result).isPresent();
        Account foundAccount = result.get();
        assertThat(foundAccount.getCustomerName()).isEqualTo("Jane Doe");
        assertThat(foundAccount.getAccountSavings()).hasSize(1);

        AccountSaving foundSaving = foundAccount.getAccountSavings().get(0);
        assertThat(foundSaving.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(foundSaving.getAvailableBalance()).isEqualByComparingTo("75.25");
        assertThat(foundSaving.getAccount().getId()).isEqualTo(savedAccount.getId());
    }
}
