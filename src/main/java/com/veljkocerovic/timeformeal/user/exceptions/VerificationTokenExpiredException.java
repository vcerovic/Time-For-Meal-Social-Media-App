package com.veljkocerovic.timeformeal.user.exceptions;

public class VerificationTokenExpiredException extends Exception{
    public VerificationTokenExpiredException() {
    }

    public VerificationTokenExpiredException(String message) {
        super(message);
    }

    public VerificationTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationTokenExpiredException(Throwable cause) {
        super(cause);
    }

    public VerificationTokenExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
