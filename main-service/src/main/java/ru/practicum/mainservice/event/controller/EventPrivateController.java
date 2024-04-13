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
        Event event = eventService.create(userId, eventNewDto);
        log.info("Пользователь с id {}, добавляет событие {} ", userId, eventNewDto.getAnnotation());
        return EventMapper.toEventFullDto(event);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<EventShortDto> getAllInitiatorEvents(
            @PathVariable Long userId,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        List<Event> result = eventService.getAllByInitiator(userId, from, size);
        log.info("List events for User Id {}. Where from = {}, size = {}", userId, from, size);
        return result.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EventFullDto getInitiatorEvent(@PathVariable Long userId,
                                         @PathVariable Long eventId) {
        Event event = eventService.getByInitiator(userId, eventId);
        log.info("Автор с id: {} запросил свое событие с id: {} ", eventId, userId);
        return EventMapper.toEventFullDto(event);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EventFullDto updateEventByUserId(@RequestBody @Valid EventUpdateDto eventUpdateDto,
                                            @PathVariable Long userId,
                                            @PathVariable Long eventId) {
        Event updatedEvent = eventService.updateByInitiator(eventUpdateDto, userId, eventId);
        log.info("Пользователь с id{}, обновил событие {} ", eventId, eventUpdateDto.getAnnotation());
        return EventMapper.toEventFullDto(updatedEvent);
    }

    @GetMapping("/{eventId}/requests") // todo - заполнить методы после создания функционала запросов
    @ResponseStatus(value = HttpStatus.OK)
    public List<EventFullDto> getRequestsForEventIdByUserId(@PathVariable Long userId, @PathVariable Long eventId) {
        return null;
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(value = HttpStatus.OK)
    public EventFullDto updateStatusRequestsForEventIdByUserId(@PathVariable Long userId,
                                                               @PathVariable Long eventId,
                                                               @RequestBody EventUpdateDto eventUpdateDto) {
        return null;
    }

}
