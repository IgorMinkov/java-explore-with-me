package ru.practicum.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.event.dto.LocationDto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.event.model.event.EventMapper.DATE_FORMAT;

@Data
@Builder
public class EventNewDto {

    @NotBlank(message = "annotation cannot consist only of spaces")
    @Size(min = 20, max = 2000, message = "annotation must be greater than 20 and less than 2000")
    private String annotation;

    @NotNull(message = "category cannot be null")
    private Long category;

    @NotBlank(message = "title cannot consist only of spaces")
    @Size(min = 3, max = 120, message = "title must be greater than 3 and less than 120")
    private String title;

    @NotBlank(message = "description cannot consist only of spaces")
    @Size(min = 20, max = 7000, message = "description must be greater than 20 and less than 7000")
    private String description;

    @NotNull
    @FutureOrPresent(message = "event date must be today or in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDateTime eventDate;

    @NotNull(message = "location cannot be null")
    private LocationDto location;

    @Builder.Default
    private Boolean paid = false;

    @Builder.Default
    private Long participantLimit = 0L;

    @Builder.Default
    private Boolean requestModeration = true;

}
