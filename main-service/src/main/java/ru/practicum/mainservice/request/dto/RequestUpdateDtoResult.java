package ru.practicum.mainservice.request.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestUpdateDtoResult {

    private List<RequestDto> confirmedRequests;

    private List<RequestDto> rejectedRequests;

}
