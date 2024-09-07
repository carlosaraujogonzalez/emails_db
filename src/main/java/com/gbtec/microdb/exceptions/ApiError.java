package com.gbtec.microdb.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {

    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
}