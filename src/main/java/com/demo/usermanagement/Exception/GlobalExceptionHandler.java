package com.demo.usermanagement.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles the HttpRequestMethodNotSupportedException by returning a ResponseEntity with an error message and the appropriate HttpStatus.
     *
     * @param  ex       the HttpRequestMethodNotSupportedException that was thrown
     * @param  headers  the HttpHeaders for the response
     * @param  status   the HttpStatus for the response
     * @param  request  the WebRequest for the response
     * @return          the ResponseEntity with the error message and the appropriate HttpStatus
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Method not allowed. Please check the request method.";
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
