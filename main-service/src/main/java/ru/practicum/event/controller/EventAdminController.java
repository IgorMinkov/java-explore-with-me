package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

@Slf4j
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventService eventService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<EventFullDto> getEventsByAdmin(
            @RequestParam(name = "users", required = false) List<Long> users,
            @RequestParam(name = "states", required = false) List<String> states,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "rangeStart", required = false) String rangeStart,
            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

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
