package com.codegym.exception;

public class UnavailableBalanceException extends Exception{
    private final String message = "There is unvailable balance";

    @Override
    public String getMessage() {
        return message;
    }
}
