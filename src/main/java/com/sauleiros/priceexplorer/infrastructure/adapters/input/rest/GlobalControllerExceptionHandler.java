package com.sauleiros.priceexplorer.infrastructure.adapters.input.rest;

import com.sauleiros.priceexplorer.domain.exception.PriceNotFoundException;
import com.sauleiros.priceexplorer.infrastructure.adapters.input.rest.models.ErrorMessageModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorMessageModel> handleRuntimeException(RuntimeException exception) {
        ErrorMessageModel errorMessage = new ErrorMessageModel(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorMessageModel> handleMismatchParameterException(MethodArgumentTypeMismatchException exception) {
        ErrorMessageModel errorMessage = new ErrorMessageModel(
                HttpStatus.BAD_REQUEST.value(),
                String.format("The %s parameter is not of the correct type: %s",
                        exception.getParameter().getParameterName(),
                        exception.getParameter().getParameterType().getSimpleName())
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorMessageModel> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        ErrorMessageModel errorMessage = new ErrorMessageModel(
                HttpStatus.BAD_REQUEST.value(),
                String.format("The %s parameter is missing",
                        exception.getParameterName())
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorMessageModel> handlePriceNotFoundException(PriceNotFoundException exception) {
        ErrorMessageModel errorMessage = new ErrorMessageModel(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
