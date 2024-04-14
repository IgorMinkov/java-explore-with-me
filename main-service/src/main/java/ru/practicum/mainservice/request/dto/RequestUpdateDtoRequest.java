package ru.practicum.mainservice.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.mainservice.utils.enums.Status;

import java.util.List;

@Data
@Builder
public class RequestUpdateDtoRequest {

    private List<Long> requestIds;

    private Status status;

}
