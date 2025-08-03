package ru.practicum.dto;

import lombok.Data;

@Data
public class EventRequestStatusUpdateResult {

    private ParticipationRequestDto confirmedRequests;

    private ParticipationRequestDto rejectedRequests;
}
