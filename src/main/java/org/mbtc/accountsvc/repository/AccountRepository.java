package org.mbtc.accountsvc.repository;

import org.mbtc.accountsvc.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {


    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.accountSavings WHERE a.id = :id")
    Optional<Account> findAccountByIdWithSavings(@Param("id") Long id);

}
