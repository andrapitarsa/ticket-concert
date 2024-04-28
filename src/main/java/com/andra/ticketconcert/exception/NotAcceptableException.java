package com.andra.ticketconcert.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableException extends RuntimeException{
    private Integer responseStatusCode;

    public NotAcceptableException(String message){
        super(message);
    }

    public NotAcceptableException(String message, int responseStatusCode){
        super(message);
        this.responseStatusCode = responseStatusCode;
    }
    public NotAcceptableException(){
        super();
    }

    public Integer getResponseStatusCode() {
        return responseStatusCode;
    }
}
