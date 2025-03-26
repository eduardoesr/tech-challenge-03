package br.com.fiap.restaurante.controller.exception;

import br.com.fiap.restaurante.error.StandardError;
import br.com.fiap.restaurante.error.service.CreateReservaValidationError;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.error.validation.ValidateError;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Hidden
@RestControllerAdvice
public class ControllerExceptionHandler {
    private StandardError err = new StandardError();

    @ExceptionHandler(NotFoundServiceError.class)
    public ResponseEntity<StandardError> entityNotFound(
            NotFoundServiceError e,
            HttpServletRequest req
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        err.setTimeInstant(Instant.now());
        err.setStatus(status.value());
        err.setError("Entity not found");
        err.setMessage(e.getMessage());
        err.setPath(req.getRequestURI());

        return ResponseEntity.status(status).body(this.err);
    }

    @ExceptionHandler(CreateReservaValidationError.class)
    public ResponseEntity<StandardError> entityNotFound(
            CreateReservaValidationError e,
            HttpServletRequest req
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        err.setTimeInstant(Instant.now());
        err.setStatus(status.value());
        err.setError("Erro de validação");
        err.setMessage(e.getMessage());
        err.setPath(req.getRequestURI());

        return ResponseEntity.status(status).body(this.err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(
            MethodArgumentNotValidException e,
            HttpServletRequest req
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ValidateError validateError = new ValidateError();

        validateError.setTimeInstant(Instant.now());
        validateError.setStatus(status.value());
        validateError.setError("Erro de validação");
        validateError.setMessage(e.getMessage());
        validateError.setPath(req.getRequestURI());

        for (FieldError f: e.getBindingResult().getFieldErrors()) {
            validateError.addMessages(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(validateError);
    }
}