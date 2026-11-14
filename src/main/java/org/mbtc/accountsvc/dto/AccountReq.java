package org.mbtc.accountsvc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountReq {

    @NotNull(message = "customerName is required")
    private String customerName;
    @NotNull(message = "customerMobile is required")
    private String customerMobile;
    @NotNull(message = "customerEmail is required")
    @Email(message = "Please provide a valid email address")
    private String customerEmail;
    @NotNull(message = "addressLineOne is required")
    private String addressLineOne;
    private String addressLineTwo;
    @Valid
    @NotNull(message = "accountSavingReq is required")
    private AccountSavingReq accountSavingReq;
}
