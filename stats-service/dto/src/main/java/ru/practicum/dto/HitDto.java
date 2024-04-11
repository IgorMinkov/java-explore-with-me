package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
public class HitDto {

    @Positive(message = "id must be positive")
    private Long id;

    @NotBlank(message = "app cannot consist only of spaces")
    private String app;

    @NotBlank(message = "uri cannot consist only of spaces")
    private String uri;

    @NotBlank(message = "ip cannot consist only of spaces")
    private String ip;

    @NotBlank(message = "timestamp cannot consist only of spaces.")
    private String timestamp;

}
