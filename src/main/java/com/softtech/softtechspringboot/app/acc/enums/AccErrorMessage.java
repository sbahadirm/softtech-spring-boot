package com.softtech.softtechspringboot.app.acc.enums;

import com.softtech.softtechspringboot.app.gen.enums.BaseErrorMessage;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public enum AccErrorMessage implements BaseErrorMessage {

    INSUFFICIENT_BALANCE("Insufficient balance!"),
    ;

    private String message;
    AccErrorMessage(String message) {
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
