package org.example.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.example.client.ProductServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        log.info("Caught IllegalArgumentException");
        log.info("Exception " + ex);
        ApiError body = new ApiError(Instant.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
        log.info("Bad Request: " + body);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ApiError> productsError(ProductServiceException ex, HttpServletRequest request) {
        log.info("Caught productServiceException");
        log.info("Exception " + ex);
        ApiError body = new ApiError(Instant.now(), HttpStatus.BAD_GATEWAY.value(), HttpStatus.BAD_GATEWAY.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
        log.info("ProductServiceException: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ApiError> handleMyExcpetion(MyException ex, HttpServletRequest request) {
        log.info("Caught MyException");
        log.info("Exception " + ex);
        int status = ex.getStatus();
        HttpStatus httpStatus = HttpStatus.valueOf(status);
        String error = httpStatus!=null?httpStatus.getReasonPhrase():ex.getMessage();
        ApiError body = new ApiError(Instant.now(), status, error, ex.getMessage(), request.getRequestURI());
        log.info("ProductServiceException: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> unknownError(Exception ex, HttpServletRequest request) {
        ApiError body = new ApiError(Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
        log.debug("Exception: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }


}
