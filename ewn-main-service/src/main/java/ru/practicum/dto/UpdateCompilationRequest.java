package ru.practicum.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCompilationRequest {

    private Boolean painned;

    private String title;

    private List<Integer> events;

}
