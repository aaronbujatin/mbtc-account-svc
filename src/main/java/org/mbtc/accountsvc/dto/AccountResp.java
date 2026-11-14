package org.mbtc.accountsvc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResp {

    private Long customerNumber;
    private int transactionStatusCode;
    private String transactionStatusDescription;
    private List<AccountSavingResp> savings;

}
