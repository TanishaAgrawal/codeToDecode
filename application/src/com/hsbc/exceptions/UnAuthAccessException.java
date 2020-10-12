package com.hsbc.exceptions;

public class UnAuthAccessException extends Exception{
    public UnAuthAccessException(String message){
        super(message);
    }
    public UnAuthAccessException(String message,Throwable throwable){
        super(message,throwable);
    }    
}
