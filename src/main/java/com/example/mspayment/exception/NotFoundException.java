package com.example.mspayment.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends Exception {

    private final String code;

    public NotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

}