package ru.practicum.dto;

import lombok.Data;

@Data
public class EventShortDto {

    private Integer id;

    private String annotation;

    private CategoryDto category;

    private Integer confirmedRequests;

    private String eventDate;

    private UserShortDto userShortDto;

    private Boolean paid;

    private String title;

    private Integer views;
}
