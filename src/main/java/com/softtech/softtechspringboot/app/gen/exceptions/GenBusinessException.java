package com.softtech.softtechspringboot.app.gen.exceptions;

import com.softtech.softtechspringboot.app.gen.enums.BaseErrorMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenBusinessException extends RuntimeException{

    private BaseErrorMessage baseErrorMessage;
    private String message;

    public GenBusinessException(BaseErrorMessage baseErrorMessage) {
        this.baseErrorMessage = baseErrorMessage;
        this.message = baseErrorMessage.getMessage();
    }

    public GenBusinessException(BaseErrorMessage baseErrorMessage, String... parameters) {

        super(String.format(baseErrorMessage.getMessage(), parameters));

        this.baseErrorMessage = baseErrorMessage;
        this.message = String.format(baseErrorMessage.getMessage(), parameters);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
