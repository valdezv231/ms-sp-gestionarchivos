package com.example.msspgestionarchivos.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(
            InvalidFileTypeException.class
    )
    public ResponseEntity<Map<String,String>>handleInvalidFile(InvalidFileTypeException ex) {

        log.warn("Archivo inválido: {}",
                ex.getMessage());

        return ResponseEntity.badRequest()
                .body(Map.of(
                        "error",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>>handleGeneral(Exception ex) {

        log.error("Error inesperado", ex);
        return ResponseEntity.internalServerError()
                .body(Map.of(
                        "error",
                        "Error interno del servidor"
                ));
    }

    @ExceptionHandler(MissingHeaderException.class)
    public ResponseEntity<Map<String, Object>> handleMissingHeaders(
            MissingHeaderException ex
    ) {

        log.warn("Validación de headers fallida: {}", ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "error", "VALIDATION_ERROR",
                        "message", ex.getMessage()
                ));
    }
}