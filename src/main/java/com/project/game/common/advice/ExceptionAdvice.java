package com.project.game.common.advice;

import com.project.game.common.dto.ErrorResponse;
import com.project.game.common.exception.EntityNotFoundException;
import com.project.game.common.exception.ValueInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * [EntityNotFoundException] 발생 시 404 NOT FOUND 응답
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandler(EntityNotFoundException exception){
        return new ResponseEntity<>(new ErrorResponse(exception), HttpStatus.NOT_FOUND);
    }

    /**
     * [ValueInvalidException] 발생 시 400 BAD_REQUEST 응답
     */
    @ExceptionHandler(ValueInvalidException.class)
    public ResponseEntity<ErrorResponse> valueInvalidExceptionHandler(ValueInvalidException exception){
        return new ResponseEntity<>(new ErrorResponse(exception), HttpStatus.BAD_REQUEST);
    }
}
