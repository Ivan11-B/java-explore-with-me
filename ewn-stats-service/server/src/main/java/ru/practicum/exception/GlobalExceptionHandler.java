package ru.practicum.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleValidationException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new Error("Ошибка валидации даты", "Неверный формат даты"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseEntity.badRequest().body(new Error("Ошибка валидации", errorMessage));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleValidationException(ConstraintViolationException ex) {
        String message = String.join(",", ex.getConstraintViolations().stream()
                .map(v -> v.getMessage())
                .collect(Collectors.toList()));

        return ResponseEntity.badRequest().body(new Error("Ошибка валидации", message));
    }

    @ExceptionHandler(IntervalTimeException.class)
    public ResponseEntity<Error> handlerTimeException(IntervalTimeException ex) {
        return ResponseEntity.badRequest().body(new Error("Ошибка временного интервала", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error("INTERNAL_ERROR", "Внутренняя ошибка сервера"));
    }
}