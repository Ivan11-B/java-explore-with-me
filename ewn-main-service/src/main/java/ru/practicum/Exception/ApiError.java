package ru.practicum.Exception;

import lombok.Data;

@Data
public class ApiError {

    private String message;

    private String reason;

    private String status;

    private String timestamp;

//    private List<Error> errors;

}
