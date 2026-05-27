package com.finpulse.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateBudgetException extends RuntimeException {
    public DuplicateBudgetException(String message){
        super(message);
    }
}
