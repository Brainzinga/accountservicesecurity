package com.dxc.accountservice.exception;

public class CustomerNotfoundException extends RuntimeException{

    public CustomerNotfoundException(String message) {
        super(message);
    }
}
