package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class LocationDto {

    @NotNull(message = "latitude cannot be null")
    private Float lat;

    @NotNull(message = "longitude cannot be null")
    private Float lon;

}
