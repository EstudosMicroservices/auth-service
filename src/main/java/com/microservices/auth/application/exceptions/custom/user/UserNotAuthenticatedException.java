package com.microservices.auth.application.exceptions.custom.user;

import com.microservices.auth.application.exceptions.BaseException;
import org.springframework.http.HttpStatusCode;

public class UserNotAuthenticatedException extends BaseException {

    public UserNotAuthenticatedException(String detail) {
        super(HttpStatusCode.valueOf(204), "User not authenticated!", detail);
    }
}