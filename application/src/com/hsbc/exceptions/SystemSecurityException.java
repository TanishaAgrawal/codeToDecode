package com.hsbc.exceptions;

public class SystemSecurityException extends Exception{
    public SystemSecurityException(String message){
        super(message);
    }
    public SystemSecurityException(String message,Throwable throwable){
        super(message,throwable);
    }    
}
