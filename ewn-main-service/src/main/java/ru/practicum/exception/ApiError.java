package ru.practicum.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

    private String message;

    private String reason;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String timestamp;

//    private List<Error> errors;

}
