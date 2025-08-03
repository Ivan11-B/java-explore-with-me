package ru.practicum.dto;

import lombok.Data;

@Data
public class NewUserRequest {

    private String email;

    private String name;
}
