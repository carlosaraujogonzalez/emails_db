package com.gbtec.microdb.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { EmailExistsException.class })
    protected ResponseEntity<ApiError> handleEmailExistsException(Exception ex, WebRequest request) {

        String bodyOfResponse = "This Email is already saved in the database";
        ApiError apierror = new ApiError(HttpStatus.BAD_REQUEST, bodyOfResponse);
        return new ResponseEntity<ApiError>(apierror, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = { UpdateFailedException.class })
    protected ResponseEntity<ApiError> handleUpdateFailedException(Exception ex, WebRequest request) {

        String bodyOfResponse = "This Email cannot be updated because its status is not 2-EMAIL_STATE_BORRADOR";
        ApiError apierror = new ApiError(HttpStatus.BAD_REQUEST, bodyOfResponse);
        return new ResponseEntity<ApiError>(apierror, HttpStatus.BAD_REQUEST);
    }
}