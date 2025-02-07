package org.compass.desafio2.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.compass.desafio2.exception.BadRequestException;
import org.compass.desafio2.exception.EntityNotFoundException;
import org.compass.desafio2.exception.UniqueViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        String msg = "Recurso " + ex.getResource() + " com ID " + ex.getCode() + " não encontrado.";
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, msg));
    }

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<ErrorMessage> uniqueViolation(UniqueViolationException ex, HttpServletRequest request) {
        log.error("Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> badRequestException(BadRequestException ex, HttpServletRequest request) {
        String msg = ex.getMsg() != null ?
                ex.getMsg() :
                "Recurso " + ex.getResource() + " com ID " + ex.getCode() + " não encontrado.";

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, msg));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String paramName = ex.getParameter().getParameterName();
        String invalidValue = ex.getValue() != null ? ex.getValue().toString() : "null";

        String errorMessage = String.format(
                "Parâmetro '%s' com valor '%s' é inválido. O ID deve ser um número válido.",
                paramName,
                invalidValue
        );

        BadRequestException badRequestEx = new BadRequestException(errorMessage);
        return badRequestException(badRequestEx, request);
    }
}
