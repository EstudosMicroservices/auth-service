package com.microservices.auth.application.exceptions.custom.user;

import com.microservices.auth.application.exceptions.BaseException;
import org.springframework.http.HttpStatusCode;

public class UserAlreadyAuthenticatedException extends BaseException {

    public UserAlreadyAuthenticatedException(String detail) {
        super(HttpStatusCode.valueOf(409), "User already authenticated!", detail);
    }
}