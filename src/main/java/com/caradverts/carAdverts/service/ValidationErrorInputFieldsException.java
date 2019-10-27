package com.caradverts.carAdverts.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValidationErrorInputFieldsException extends RuntimeException{

    public ValidationErrorInputFieldsException(String message) {
        super(message);
    }


}
