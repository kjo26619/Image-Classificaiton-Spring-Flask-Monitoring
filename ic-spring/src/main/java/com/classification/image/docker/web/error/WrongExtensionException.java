package com.classification.image.docker.web.error;

public class WrongExtensionException extends RuntimeException {

    public WrongExtensionException(String msg) {
        super(msg);
    }
}
