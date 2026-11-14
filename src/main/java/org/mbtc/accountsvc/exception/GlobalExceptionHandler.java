package org.mbtc.accountsvc.exception;

import org.mbtc.accountsvc.dto.ErrorResp;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResp> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex
    ) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("Validation failed");

        ErrorResp response = new ErrorResp(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }


    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResp> handleCustomerNotFound(CustomerNotFoundException ex) {
        ErrorResp response = new ErrorResp(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    private ResponseEntity<ErrorResp> buildErrorResponse(HttpStatus status, String description) {
        ErrorResp response = new ErrorResp(
                status.value(),
                description
        );

        return ResponseEntity.status(status).body(response);
    }

}
