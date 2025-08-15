package ru.practicum.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class UpdateEventUserRequest {

    @Size(min = 20, max = 2000, message = "Описание должно быть от 20 до 2000 символов")
    private String annotation;

    private Integer category;

    @Size(min = 20, max = 7000, message = "Полное описание должно быть от 20 до 7000 символов")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;

    private Location location;

    private Boolean paid;

    @Positive(message = "Лимит участников не может быть отрицательным")
    private Integer participantLimit;

    private Boolean requestModeration;

    private String stateAction;

    @Size(min = 3, max = 120, message = "Заголовок должен быть от 3 до 120 символов")
    private String title;
}
