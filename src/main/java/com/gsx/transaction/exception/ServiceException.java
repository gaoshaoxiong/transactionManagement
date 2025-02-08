package com.gsx.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;

/**
 * Service business Exception
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    private ProblemDetail body;

    public ServiceException() {
    }

    public ServiceException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }

}