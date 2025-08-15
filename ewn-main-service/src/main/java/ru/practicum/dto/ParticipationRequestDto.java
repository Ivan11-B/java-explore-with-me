package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipationRequestDto {

    private String created;

    private Integer event;

    private Integer id;

    private Integer requester;

    private String status;
}
