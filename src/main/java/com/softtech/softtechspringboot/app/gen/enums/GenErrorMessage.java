package com.softtech.softtechspringboot.app.gen.enums;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public enum GenErrorMessage implements BaseErrorMessage{

    ITEM_NOT_FOUND("Item not found!"),
    DATE_COULD_NOT_BE_CONVERTED("Date could not be converted!")
    ;

    private String message;
    GenErrorMessage(String message) {
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
