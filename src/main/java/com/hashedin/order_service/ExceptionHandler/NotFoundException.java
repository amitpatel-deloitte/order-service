package com.hashedin.order_service.ExceptionHandler;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
}
