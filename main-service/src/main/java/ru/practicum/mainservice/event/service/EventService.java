package ru.practicum.mainservice.event.service;

import ru.practicum.mainservice.event.dto.event.EventNewDto;
import ru.practicum.mainservice.event.dto.event.EventUpdateDto;
import ru.practicum.mainservice.event.model.event.Event;

import java.util.List;

public interface EventService {

    Event create(Long userId, EventNewDto eventnewDto);

    List<Event> getAllByInitiator(Long userId, Integer from, Integer size);

    Event getByInitiator(Long userId, Long eventId);

    Event updateByInitiator(EventUpdateDto eventUpdateDto, Long userId, Long eventId);

    Event updateByAdmin(EventUpdateDto eventUpdateDto, Long eventId);

    List<Event> getByAdmin(List<Long> users, List<String> states, List<Long> categories,
                           String startTime, String endTime, Integer from, Integer size);

    Event getById(Long eventId, String uri, String ip);

    List<Event> getByPublic(String text, List<Long> categories, Boolean paid, String startTime, String endTime,
                            Boolean onlyAvailable, String sort, Integer from, Integer size, String uri, String ip);

}
