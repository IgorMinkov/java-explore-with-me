package ru.practicum.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.dto.LocationDto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static ru.practicum.Utils.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventNewDto {

    @NotBlank(message = "annotation cannot consist only of spaces")
    @Size(min = 20, max = 2000, message = "annotation must be greater than 20 and less than 2000")
    private String annotation;

    @NotNull(message = "category cannot be null")
    private Long category;

    @NotBlank(message = "description cannot consist only of spaces")
    @Size(min = 20, max = 7000, message = "description must be greater than 20 and less than 7000")
    private String description;

    @FutureOrPresent
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime eventDate;

    @NotNull(message = "location cannot be null")
    private LocationDto location;

    @Builder.Default
    private Boolean paid = false;

    @PositiveOrZero
    @Builder.Default
    private Long participantLimit = 0L;

    @Builder.Default
    private Boolean requestModeration = true;

    @NotBlank(message = "title cannot consist only of spaces")
    @Size(min = 3, max = 120, message = "title must be greater than 3 and less than 120")
    private String title;

}
