package org.mbtc.accountsvc.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mbtc.accountsvc.enums.AccountType;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountSavingReq {

    @NotNull(message = "accountType is required")
    private AccountType accountType;
    @NotNull(message = "availableBalance is required")
    private BigDecimal availableBalance;
}
