package com.xm.crypto.controller;

import com.xm.crypto.dto.ErrorDTO;
import com.xm.crypto.exception.NoDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<ErrorDTO> handleNoDataException(NoDataException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

}
