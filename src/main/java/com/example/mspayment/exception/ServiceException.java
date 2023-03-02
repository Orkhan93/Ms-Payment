package com.example.mspayment.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final String code;
    private final String message;

    public ServiceException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ServiceException of(String code, String message) {
        return new ServiceException(code, message);
    }

}