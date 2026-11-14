package org.mbtc.accountsvc.service;

import org.mbtc.accountsvc.dto.AccountReq;
import org.mbtc.accountsvc.dto.AccountResp;

public interface AccountService {


    Long createCustomerAccount(AccountReq accountReq);

    AccountResp getCustomerAccount(Long customerId);

}
