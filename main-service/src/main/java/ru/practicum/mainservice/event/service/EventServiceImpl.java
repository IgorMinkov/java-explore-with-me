package ru.practicum.mainservice.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.event.dto.event.EventNewDto;
import ru.practicum.mainservice.event.dto.event.EventUpdateDto;
import ru.practicum.mainservice.event.model.event.Event;
import ru.practicum.mainservice.event.repository.EventRepository;
import ru.practicum.mainservice.event.repository.LocationRepository;
import ru.practicum.mainservice.utils.EntityCheckService;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService { // todo - написать методы сервиса

    private final EventRepository eventRepository;
    private final EntityCheckService checkService;
    private final LocationRepository locationRepository;

    @Override
    public Event create(Long userId, EventNewDto eventnewDto) {
        return null;
    }

    @Override
    public List<Event> getAllByInitiator(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public Event getByInitiator(Long userId, Long eventId) {
        return null;
    }

    @Override
    public Event updateByInitiator(EventUpdateDto eventUpdateDto, Long userId, Long eventId) {
        return null;
    }

    @Override
    public Event updateByAdmin(EventUpdateDto eventUpdateDto, Long eventId) {
        return null;
    }

    @Override
    public List<Event> getByAdmin(List<Long> users, List<String> states, List<Long> categories,
                                  String startTime, String endTime, Integer from, Integer size) {
        return null;
    }

    @Override
    public Event getById(Long eventId, String uri, String ip) {
        return null;
    }

    @Override
    public List<Event> getByPublic(String text, List<Long> categories, Boolean paid, String startTime, String endTime,
                                   Boolean onlyAvailable, String sort, Integer from, Integer size,
                                   String uri, String ip) {
        return null;
    }
}
