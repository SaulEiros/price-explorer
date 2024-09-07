package com.sauleiros.priceexplorer.infrastructure.adapters.input.rest;

import com.sauleiros.priceexplorer.infrastructure.adapters.input.rest.models.ErrorMessageModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorMessageModel> handleRuntimeException(RuntimeException exception) {
        ErrorMessageModel errorMessage = new ErrorMessageModel(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
        );

        return new ResponseEntity<ErrorMessageModel>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
