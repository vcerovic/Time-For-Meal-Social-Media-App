package com.veljkocerovic.timeformeal.exceptions;

public class RecipeNotFoundException extends Exception{
    public RecipeNotFoundException() {
        super();
    }

    public RecipeNotFoundException(String message) {
        super(message);
    }

    public RecipeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecipeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected RecipeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
