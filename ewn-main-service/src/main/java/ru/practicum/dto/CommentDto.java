package ru.practicum.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    private Integer id;

    @Size(min = 5, max = 500, message = "Комментарий должн быть от 5 до 500 символов")
    private String text;

    private String authorName;

    private LocalDateTime created;
}