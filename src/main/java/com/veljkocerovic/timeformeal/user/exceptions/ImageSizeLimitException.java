package com.veljkocerovic.timeformeal.user.exceptions;

public class ImageSizeLimitException extends Exception{
    public ImageSizeLimitException() {
    }

    public ImageSizeLimitException(String message) {
        super(message);
    }

    public ImageSizeLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageSizeLimitException(Throwable cause) {
        super(cause);
    }

    public ImageSizeLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
