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

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiError> handlerNumberFormatException(NumberFormatException ex) {
        ApiError apiError = ApiError.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message(ex.getMessage())
                .reason("Передоваемый параметр должен быть числом")
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


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

    @ExceptionHandler({UserDuplicateException.class, CategoryDuplicateException.class})
    public ResponseEntity<ApiError> handleDuplicateEmail(RuntimeException ex) {
        ApiError apiError = ApiError.builder()
                .status(String.valueOf(HttpStatus.CONFLICT))
                .message(ex.getMessage())
                .reason("Дубликат данных")
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

    @ExceptionHandler(OwnerEventException.class)
    public ResponseEntity<ApiError> handleOwnerEvent(OwnerEventException ex) {
        ApiError apiError = ApiError.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message(ex.getMessage())
                .reason("Ошибка данных")
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(UpdateConflictException.class)
    public ResponseEntity<ApiError> handleUpdateEvent(UpdateConflictException ex) {
        ApiError apiError = ApiError.builder()
                .status(String.valueOf(HttpStatus.CONFLICT))
                .message(ex.getMessage())
                .reason("Ошибка данных")
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(ParticipationException.class)
    public ResponseEntity<ApiError> handleUpdateEvent(ParticipationException ex) {
        ApiError apiError = ApiError.builder()
                .status(String.valueOf(HttpStatus.CONFLICT))
                .message(ex.getMessage())
                .reason("Ошибка данных")
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(DeleteCategoryException.class)
    public ResponseEntity<ApiError> handleUpdateEvent(DeleteCategoryException ex) {
        ApiError apiError = ApiError.builder()
                .status(String.valueOf(HttpStatus.CONFLICT))
                .message(ex.getMessage())
                .reason("Существуют события, связанные с категорией")
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(EventDateException.class)
    public ResponseEntity<ApiError> handleUpdateEvent(EventDateException ex) {
        ApiError apiError = ApiError.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message(ex.getMessage())
                .reason("Событие не удовлетворяет правилам создания")
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}