package ru.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<Error> handleValidationException(HttpMessageNotReadableException ex) {
//        return ResponseEntity.badRequest().body(new Error("Ошибка валидации даты", "Неверный формат даты"));
//    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handlerTimeException(NotFoundException ex) {
        ApiError apiError = ApiError.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message(ex.getMessage())
                .reason("Искомый объект не был найден")
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(UserDuplicateException.class)
    public ResponseEntity<ApiError> handleDuplicateEmail(UserDuplicateException ex) {
        ApiError apiError = ApiError.builder()
                .status(String.valueOf(HttpStatus.CONFLICT))
                .message(ex.getMessage())
                .reason("Email уже существует")
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ApiError apiError = ApiError.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message(errorMessage)
                .reason("Некорректный ввод данных")
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.badRequest().body(apiError);
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Error> handleValidationException(ConstraintViolationException ex) {
//        String message = String.join(",", ex.getConstraintViolations().stream()
//                .map(v -> v.getMessage())
//                .collect(Collectors.toList()));
//
//        return ResponseEntity.badRequest().body(new Error("Ошибка валидации", message));
//    }

//    @ExceptionHandler(IntervalTimeException.class)
//    public ResponseEntity<Error> handlerTimeException(IntervalTimeException ex) {
//        return ResponseEntity.badRequest().body(new Error("Ошибка временного интервала", ex.getMessage()));
//    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Error> handleAllExceptions(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new Error("INTERNAL_ERROR", "Внутренняя ошибка сервера"));
//    }
}