package com.softtech.softtechspringboot.app.cus.enums;

import com.softtech.softtechspringboot.app.gen.enums.BaseErrorMessage;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public enum CusErrorMessage implements BaseErrorMessage {

    CUSTOMER_ERROR_MESSAGE("Customer not found!"),
    ;

    private String message;
    CusErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
