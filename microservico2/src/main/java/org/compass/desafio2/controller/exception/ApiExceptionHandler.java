package org.compass.desafio2.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.compass.desafio2.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(NotFoundException ex, HttpServletRequest request) {
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

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessage> handleConflictException(
            ConflictException ex,
            HttpServletRequest request
    ) {
        ErrorMessage error = new ErrorMessage(
                request,
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            String message = error.getDefaultMessage();
            errors.put(error.getField(), message);
        }

        ErrorMessage errorMessage = new ErrorMessage(
                request,
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Erro de validação nos campos",
                errors
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessage);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(
            ValidationException ex,
            HttpServletRequest request
    ) {
        ErrorMessage error = new ErrorMessage(
                request,
                HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage(),
                ex.getErrors()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorMessage> handleInternalServerError(
            InternalServerErrorException ex,
            HttpServletRequest request
    ) {
        log.error("Erro interno: ", ex);
        ErrorMessage error = new ErrorMessage(
                request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado. Tente novamente mais tarde."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("Erro não tratado: ", ex);
        ErrorMessage error = new ErrorMessage(
                request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno no servidor. Contate o suporte."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
