package org.mbtc.accountsvc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mbtc.accountsvc.enums.AccountType;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountSavingResp {
    private Long accountNumber;
    private AccountType accountType;
    private BigDecimal availableBalance;
}
