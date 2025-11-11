package com.microservices.auth.application.exceptions.custom.token;

import com.microservices.auth.application.exceptions.BaseException;
import org.springframework.http.HttpStatusCode;

public class TokenAlreadyExistsException extends BaseException {

    public TokenAlreadyExistsException(String detail) {
        super(HttpStatusCode.valueOf(409), "Token already exists!", detail);
    }
}