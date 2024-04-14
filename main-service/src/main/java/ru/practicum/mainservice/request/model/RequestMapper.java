package ru.practicum.mainservice.request.model;

import lombok.experimental.UtilityClass;
import ru.practicum.mainservice.request.dto.RequestDto;
import ru.practicum.mainservice.utils.enums.Status;

import java.time.LocalDateTime;

@UtilityClass
public class RequestMapper {

    public static Request toRequest(RequestDto requestDto) {
        return Request.builder()
                .created(LocalDateTime.now())
                .status(Status.PENDING)
                .build();
    }

    public static RequestDto toRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

}
