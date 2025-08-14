package ru.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;
}

