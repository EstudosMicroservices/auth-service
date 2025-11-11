package com.microservices.auth.application.exceptions.custom.user;

import com.microservices.auth.application.exceptions.BaseException;
import org.springframework.http.HttpStatusCode;

public class UserProfileAlreadyExistsException extends BaseException {

    public UserProfileAlreadyExistsException(String detail) {
        super(HttpStatusCode.valueOf(409), "User profile already exists!", detail);
    }
}