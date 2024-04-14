package ru.practicum.mainservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.event.dto.event.EventFullDto;
import ru.practicum.mainservice.event.dto.event.EventNewDto;
import ru.practicum.mainservice.event.dto.event.EventShortDto;
import ru.practicum.mainservice.event.dto.event.EventUpdateDto;
import ru.practicum.mainservice.event.model.event.Event;
import ru.practicum.mainservice.event.model.event.EventMapper;
import ru.practicum.mainservice.event.service.EventService;
import ru.practicum.mainservice.request.dto.RequestDto;
import ru.practicum.mainservice.request.dto.RequestUpdateDtoRequest;
import ru.practicum.mainservice.request.dto.RequestUpdateDtoResult;
import ru.practicum.mainservice.request.model.Request;
import ru.practicum.mainservice.request.model.RequestMapper;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventPrivateController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto addEvent(@Valid @RequestBody EventNewDto eventNewDto, @PathVariable Long userId) {
        Event event = eventService.create(
                userId, EventMapper.toEvent(eventNewDto), eventNewDto.getCategory(), eventNewDto.getLocation());
        log.info("Пользователь с id {}, добавляет событие {}", userId, eventNewDto.getAnnotation());
        return EventMapper.toEventFullDto(event);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<EventShortDto> getAllInitiatorEvents(
            @PathVariable Long userId,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        List<Event> result = eventService.getAllByInitiator(userId, from, size);
        log.info("Получение списка событий для пользователя с id: {}." +
                "Параметры from = {}, size = {}", userId, from, size);
        return result.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EventFullDto getInitiatorEvent(@PathVariable Long userId,
                                         @PathVariable Long eventId) {
        Event event = eventService.getByInitiator(userId, eventId);
        log.info("Автор с id: {} запросил свое событие с id: {}", eventId, userId);
        return EventMapper.toEventFullDto(event);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EventFullDto updateEventByInitiator(@RequestBody @Valid EventUpdateDto eventUpdateDto,
                                            @PathVariable Long userId,
                                            @PathVariable Long eventId) {
        Event updatedEvent = eventService.updateByInitiator(eventUpdateDto, userId, eventId);
        log.info("Пользователь с id{}, обновил событие {}", eventId, eventUpdateDto.getAnnotation());
        return EventMapper.toEventFullDto(updatedEvent);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(value = HttpStatus.OK)
    public List<RequestDto> getRequestsForEventByInitiator(@PathVariable Long userId, @PathVariable Long eventId) {
        List<Request> result = eventService.getRequestsForEventByInitiator(eventId, userId);
        log.info("Получение всех запросов события с id: {} для пользователя с id: {}", eventId, userId);
        return result.stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(value = HttpStatus.OK)
    public RequestUpdateDtoResult updateStatusRequestsForEventIdByUserId(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody RequestUpdateDtoRequest requestDto) {
        log.info("Обновление статуса события с id: {}, пользователем с id: {}", eventId, userId);
        return eventService.updateStatusRequestsForEventByInitiator(requestDto, userId, eventId);
    }

}
