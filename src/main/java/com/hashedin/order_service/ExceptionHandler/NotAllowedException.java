package com.hashedin.order_service.ExceptionHandler;

public class NotAllowedException extends RuntimeException{
    public NotAllowedException(String message){
        super(message);
    }
}
