package com.example.cards.exception;

public class NoCardFoundException extends RuntimeException {
    public NoCardFoundException(String s) {
        super(s);
    }
}
