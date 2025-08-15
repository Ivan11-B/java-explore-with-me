package ru.practicum.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


@Builder
@Data
public class NewEventDto {

    @NotBlank
    @Size(min = 20, max = 2000, message = "Описание должно быть от 20 до 2000 символов")
    private String annotation;

    @NotNull
    @Positive(message = "Id категории не может быть отрицательным")
    private Integer category;

    @NotBlank
    @Size(min = 20, max = 7000, message = "Полное описание должно быть от 20 до 7000 символов")
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;

    @NotNull
    private Location location;

    private Boolean paid;

    @PositiveOrZero(message = "Лимит участников не может быть отрицательным")
    private Integer participantLimit;

    private Boolean requestModeration;

    @NotBlank
    @Size(min = 3, max = 120, message = "Заголовок должен быть от 3 до 120 символов")
    private String title;
}
