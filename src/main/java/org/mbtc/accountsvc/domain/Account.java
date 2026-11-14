package org.mbtc.accountsvc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerMobile;
    private String customerEmail;
    private String addressLineOne;
    private String addressLineTwo;

    @Builder.Default
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountSaving> accountSavings = new ArrayList<>();

    public void addAccountSaving(AccountSaving accountSaving) {
        if (accountSavings == null) {
            accountSavings = new ArrayList<>();
        }
        accountSavings.add(accountSaving);
        accountSaving.setAccount(this);
    }
}
