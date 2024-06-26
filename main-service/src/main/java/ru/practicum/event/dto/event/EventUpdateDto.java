package ru.practicum.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.dto.LocationDto;
import ru.practicum.utils.enums.StateAction;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.Utils.DATE_FORMAT;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateDto {

    @Size(min = 20, max = 2000, message = "annotation must be greater than 20 and less than 2000")
    private String annotation;

    private Long category;

    @Size(min = 3, max = 120, message = "title must be greater than 3 and less than 120")
    private String title;

    @Size(min = 20, max = 7000, message = "description must be greater than 20 and less than 7000")
    private String description;

    @FutureOrPresent
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    @PositiveOrZero
    private Long participantLimit;

    private Boolean requestModeration;

    private StateAction stateAction;

}
