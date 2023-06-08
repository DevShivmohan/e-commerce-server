package com.ecommerce.blue.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<?> handleCustomException(CustomException customException){
        log.error(customException.toString());
        return ResponseEntity.status(customException.getStatusCode()).body(customException.getErrorMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException){
        log.error(sqlIntegrityConstraintViolationException.toString());
        return ResponseEntity.status(HttpStatus.IM_USED).body(sqlIntegrityConstraintViolationException.getMessage());
    }
}
