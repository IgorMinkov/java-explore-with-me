package ru.practicum.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.utils.enums.Status;

import java.util.List;

@Data
@Builder
public class RequestUpdateDtoRequest {

    private List<Long> requestIds;

    private Status status;

}
