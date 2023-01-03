package com.alexeymarino.botserver.exception;

public abstract class BaseException extends RuntimeException {
    public BaseException(String s) {
        super(s);
    }
}
