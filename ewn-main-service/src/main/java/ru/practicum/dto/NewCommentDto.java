package ru.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewCommentDto {

    @NotBlank
    @Size(min = 5, max = 500, message = "Комментарий должн быть от 5 до 500 символов")
    private String text;
}