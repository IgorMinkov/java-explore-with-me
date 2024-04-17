package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HitDto {

    @NotBlank(message = "app cannot consist only of spaces")
    private String app;

    @NotBlank(message = "uri cannot consist only of spaces")
    private String uri;

    @NotBlank(message = "ip cannot consist only of spaces")
    private String ip;

    @NotNull(message = "timestamp cannot be null")
    private String timestamp;

}
