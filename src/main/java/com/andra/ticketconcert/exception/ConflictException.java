package com.andra.ticketconcert.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    private Integer responseStatusCode;
    private Object objectResponseMessage;

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Integer responseStatusCode) {
        super(message);
        this.responseStatusCode = responseStatusCode;
    }

    public Integer getResponseStatusCode() {
        return responseStatusCode;
    }

    public Object getObjectResponseMessage() {
        return objectResponseMessage;
    }

    public ConflictException(String message, Object object) {
        super(message);
        this.objectResponseMessage=object;
    }

}
