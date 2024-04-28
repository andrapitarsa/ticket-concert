package com.andra.ticketconcert.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private Integer responseStatusCode;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message, Integer responseStatusCode) {
        super(message);
        this.responseStatusCode = responseStatusCode;
    }

    public BadRequestException(String message) {
        super(message);
    }

    public Integer getResponseStatusCode() {
        return responseStatusCode;
    }
}
