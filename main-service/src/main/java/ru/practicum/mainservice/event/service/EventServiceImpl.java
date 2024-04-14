package ru.practicum.mainservice.event.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatsClient;
import ru.practicum.mainservice.event.dto.event.EventNewDto;
import ru.practicum.mainservice.event.dto.event.EventUpdateDto;
import ru.practicum.mainservice.event.model.event.Event;
import ru.practicum.mainservice.event.repository.EventRepository;
import ru.practicum.mainservice.event.repository.LocationRepository;
import ru.practicum.mainservice.request.RequestRepository;
import ru.practicum.mainservice.request.dto.RequestUpdateDtoRequest;
import ru.practicum.mainservice.request.dto.RequestUpdateDtoResult;
import ru.practicum.mainservice.request.model.Request;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.utils.EntityCheckService;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;
    private final EntityCheckService checkService;
    private StatsClient statsClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Event create(Long userId, EventNewDto eventnewDto) {
        User user = checkService.getUserOrNotFound(userId);

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

    @Override
    public List<Request> getRequestsForEventIdByUserId(Long eventId, Long userId) {
        return null;
    }

    @Override
    public RequestUpdateDtoResult updateStatusRequestsForEventIdByUserId(
            RequestUpdateDtoRequest requestDto, Long eventId, Long userId) {
        return null;
    }
}
