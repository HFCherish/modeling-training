package com.thoughtworks.shopping.web.exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(Exception e) {
        super(e);
    }
}
