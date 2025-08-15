package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventFullDto {

    private Integer id;

    @NotBlank
    private String annotation;

    @NotNull
    private CategoryDto category;

    private Integer confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdOn;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;

    @NotNull
    private UserShortDto initiator;

    @NotNull
    private Location location;

    @NotNull
    private Boolean paid;

    private Integer participantLimit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String publishedOn;

    private Boolean requestModeration;

    private String state;

    @NotBlank
    private String title;

    private Long views;
}
