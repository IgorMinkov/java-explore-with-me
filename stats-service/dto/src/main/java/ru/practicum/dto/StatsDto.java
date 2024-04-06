package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
@AllArgsConstructor
public class StatsDto {

    @NotBlank(message = "app cannot consist only of spaces")
    private String app;

    @NotBlank(message = "uri cannot consist only of spaces")
    private String uri;

    @PositiveOrZero(message = "hits cannot be negative")
    private Long hits;

}
