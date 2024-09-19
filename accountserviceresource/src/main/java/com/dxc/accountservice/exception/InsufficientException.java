package com.dxc.accountservice.exception;

public class InsufficientException extends RuntimeException{
    public InsufficientException(String message) {
        super(message);
    }
}
