package org.mbtc.accountsvc.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum AccountType {
    SAVINGS('S'),
    CHECKING('C');

    private final char value;

    AccountType (char v) {
        this.value = v;
    }

//    @JsonValue
//    public char getCode() {
//        return value;
//    }
//
//    @JsonCreator
//    public static AccountType fromCode(String value) {
//        if (value == null || value.isEmpty()) {
//            return null;
//        }
//        char inputChar = value.charAt(0);
//        for (AccountType status : AccountType.values()) {
//            if (status.value == inputChar) {
//                return status;
//            }
//        }
//        throw new IllegalArgumentException("Invalid status code: " + value);
//    }
}
