package ru.practicum.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewUserRequest {

    @Size(min = 6, max = 254, message = "Email должен быть от 6 до 254 символов")
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 2, max = 250, message = "Имя должено быть от 2 до 250 символов")
    private String name;
}
