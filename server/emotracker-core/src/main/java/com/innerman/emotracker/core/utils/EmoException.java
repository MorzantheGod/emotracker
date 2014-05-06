package com.innerman.emotracker.core.utils;

/**
 * User: petrpopov
 * Date: 27.02.14
 * Time: 22:47
 */
public class EmoException extends Exception {

    private ErrorType errorType;

    public EmoException() {
    }

    public EmoException(String message) {
        super(message);
    }

    public EmoException(Throwable cause) {
        super(cause);
    }

    public EmoException(ErrorType errorType) {
        super();
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
