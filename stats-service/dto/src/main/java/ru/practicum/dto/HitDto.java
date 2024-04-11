package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

}
