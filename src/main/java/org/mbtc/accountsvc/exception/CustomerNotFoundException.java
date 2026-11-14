package org.mbtc.accountsvc.exception;

import lombok.Getter;

@Getter
public class CustomerNotFoundException extends RuntimeException {

    private final String message;

    public CustomerNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
