package ru.practicum.event.service;

import ru.practicum.event.dto.LocationDto;
import ru.practicum.event.dto.event.EventUpdateDto;
import ru.practicum.event.model.event.Event;
import ru.practicum.request.dto.RequestUpdateDtoRequest;
import ru.practicum.request.dto.RequestUpdateDtoResult;
import ru.practicum.request.model.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    Event create(Long userId, Event event, Long categoryId, LocationDto locationDto);

    List<Event> getAllByInitiator(Long userId, Integer from, Integer size);

    Event getByInitiator(Long userId, Long eventId);

    Event updateByInitiator(EventUpdateDto eventUpdateDto, Long userId, Long eventId);

    Event updateByAdmin(EventUpdateDto eventUpdateDto, Long eventId);

    List<Event> getByAdmin(List<Long> users, List<String> states, List<Long> categories,
                           String startTime, String endTime, Integer from, Integer size);

    Event getById(Long eventId, HttpServletRequest request);

    List<Event> getByPublic(String text, List<Long> categories, Boolean paid, String startTime, String endTime,
                            Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request);

    List<Request> getRequestsForEventByInitiator(Long eventId, Long userId);

    RequestUpdateDtoResult updateStatusRequestsForEventByInitiator(
            RequestUpdateDtoRequest requestDto, Long eventId, Long userId);

}
