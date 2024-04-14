package ru.practicum.mainservice.request.model;

import lombok.experimental.UtilityClass;
import ru.practicum.mainservice.request.dto.RequestDto;

@UtilityClass
public class RequestMapper {

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
