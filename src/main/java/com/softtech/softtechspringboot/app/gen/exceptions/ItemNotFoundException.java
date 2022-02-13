package com.softtech.softtechspringboot.app.gen.exceptions;

import com.softtech.softtechspringboot.app.cus.enums.CusErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException(CusErrorMessage message) {
        super(message.getMessage());
    }
}
