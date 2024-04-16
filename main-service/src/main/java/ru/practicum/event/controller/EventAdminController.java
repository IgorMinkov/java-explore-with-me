package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.event.EventFullDto;
import ru.practicum.event.dto.event.EventUpdateDto;
import ru.practicum.event.model.event.Event;
import ru.practicum.event.model.event.EventMapper;
import ru.practicum.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.Utils.STATS_DATE_FORMAT;

@Slf4j
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Validated
public class EventAdminController {

    private final EventService eventService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<EventFullDto> getEventsByAdmin(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = STATS_DATE_FORMAT) String rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = STATS_DATE_FORMAT)String rangeEnd,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {

        List<Event> result = eventService.getByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);

        log.info("Получение списка событий: users = {}, states = {}, categories = {}, rangeStart = {}, rangeEnd = {}," +
                " from = {}, size = {}", users, states, categories, rangeStart, rangeEnd, from, size);
        return result.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EventFullDto updateEventByAdmin(@Valid @RequestBody EventUpdateDto eventUpdateDto,
                                           @PathVariable Long eventId) {

        Event updatedEvent = eventService.updateByAdmin(eventUpdateDto, eventId);
        log.info("Администратор обновил событие с id: {} ", eventId);
        return EventMapper.toEventFullDto(updatedEvent);
    }

}
