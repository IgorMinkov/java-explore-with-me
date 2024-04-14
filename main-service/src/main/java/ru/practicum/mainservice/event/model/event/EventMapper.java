package ru.practicum.mainservice.event.model.event;

import lombok.experimental.UtilityClass;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.category.model.CategoryMapper;
import ru.practicum.mainservice.event.dto.event.EventFullDto;
import ru.practicum.mainservice.event.dto.event.EventNewDto;
import ru.practicum.mainservice.event.dto.event.EventShortDto;
import ru.practicum.mainservice.event.model.location.Location;
import ru.practicum.mainservice.event.model.location.LocationMapper;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.user.model.UserMapper;

import java.time.LocalDateTime;

import static ru.practicum.mainservice.utils.enums.State.PENDING;

@UtilityClass
public class EventMapper {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Event toEvent(EventNewDto eventNewDto, Category category, Location location, User user) { // todo - возможен рефактор - получать сущности для создания в сервисе
        return Event.builder()
                .initiator(user)
                .category(category)
                .location(location)
                .annotation(eventNewDto.getAnnotation())
                .title(eventNewDto.getTitle())
                .description(eventNewDto.getDescription())
                .eventDate(eventNewDto.getEventDate())
                .paid(eventNewDto.getPaid())
                .participantLimit(eventNewDto.getParticipantLimit())
                .requestModeration(eventNewDto.getRequestModeration())
                .createdOn(LocalDateTime.now())
                .views(0L)
                .state(PENDING)
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
