package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */
public class NoTypeHandlerException extends RuntimeException {
    public NoTypeHandlerException() {
    }

    public NoTypeHandlerException(String message) {
        super(message);
    }
}
