package ru.practicum.event.model.event;

import lombok.experimental.UtilityClass;
import ru.practicum.event.dto.event.EventNewDto;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.event.dto.event.EventFullDto;
import ru.practicum.event.dto.event.EventShortDto;
import ru.practicum.event.model.location.LocationMapper;
import ru.practicum.user.model.UserMapper;
import ru.practicum.utils.enums.State;

import java.time.LocalDateTime;

import static ru.practicum.Utils.STATS_FORMATTER;

@UtilityClass
public class EventMapper {

    public static Event toEvent(EventNewDto eventNewDto) {
        return Event.builder()
                .annotation(eventNewDto.getAnnotation())
                .title(eventNewDto.getTitle())
                .description(eventNewDto.getDescription())
                .eventDate(LocalDateTime.parse(eventNewDto.getEventDate(), STATS_FORMATTER))
                .paid(eventNewDto.getPaid())
                .participantLimit(eventNewDto.getParticipantLimit())
                .requestModeration(eventNewDto.getRequestModeration())
                .createdOn(LocalDateTime.now())
                .views(0L)
                .state(State.PENDING)
                .confirmedRequests(0L)
                .build();
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .title(event.getTitle())
                .description(event.getDescription())
                .paid(event.getPaid())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .participantLimit(event.getParticipantLimit())
                .createdOn(event.getCreatedOn())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .views(event.getViews())
                .confirmedRequests(event.getConfirmedRequests())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .title(event.getTitle())
                .paid(event.getPaid())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .views(event.getViews())
                .confirmedRequests(event.getConfirmedRequests())
                .build();
    }

}
