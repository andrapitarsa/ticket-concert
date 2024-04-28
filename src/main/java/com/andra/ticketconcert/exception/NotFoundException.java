package com.andra.ticketconcert.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private Integer responseStatusCode;

	public NotFoundException() {
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Integer responseStatusCode) {
		super(message);
		this.responseStatusCode = responseStatusCode;
	}

	@Override
	public String toString() {
		return "NotFoundException: "+getMessage();
	}

	public Integer getResponseStatusCode() {
		return responseStatusCode;
	}
}
