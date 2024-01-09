package com.blooddonation.commons.exceptions;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends AlertBackException {
    public UnAuthorizedException() {
        this(Utils.getMessage("UnAuthorized","errors"));
    }

    public UnAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
