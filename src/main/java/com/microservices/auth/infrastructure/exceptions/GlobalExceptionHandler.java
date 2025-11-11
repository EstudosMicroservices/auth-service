package com.microservices.auth.infrastructure.exceptions;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.microservices.auth.application.exceptions.BaseException;
import org.hibernate.SessionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ProblemDetail> handleBaseException(BaseException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(ex.getHttpStatusCode());
        problemDetail.setTitle(ex.getTitle());
        problemDetail.setDetail(ex.getDetail());
        return ResponseEntity.status(ex.getHttpStatusCode()).body(problemDetail);
    }

    @ExceptionHandler(SessionException.class)
    public ResponseEntity<ProblemDetail> handleSessionException(SessionException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(ex.getLocalizedMessage());
        problemDetail.setDetail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(AlgorithmMismatchException.class)
    public ResponseEntity<ProblemDetail> handleAlgorithmMismatchException(AlgorithmMismatchException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Algorithm mismatch!");
        problemDetail.setDetail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }
}