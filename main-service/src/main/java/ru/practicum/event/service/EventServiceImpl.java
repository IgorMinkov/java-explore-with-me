package ru.practicum.event.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.StatisticClientNew;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.CategoryService;
//import ru.practicum.StatsClient;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.dto.StatsParamsDto;
import ru.practicum.event.dto.LocationDto;
import ru.practicum.event.dto.event.EventUpdateDto;
import ru.practicum.event.model.event.Event;
import ru.practicum.event.model.location.Location;
import ru.practicum.event.model.location.LocationMapper;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.LocationRepository;
import ru.practicum.event.service.statsRecorder.StatsRecordingService;
import ru.practicum.exception.DataNotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.exception.ConflictException;
import ru.practicum.request.RequestRepository;
import ru.practicum.request.dto.RequestUpdateDtoRequest;
import ru.practicum.request.dto.RequestUpdateDtoResult;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestMapper;
import ru.practicum.user.model.User;
import ru.practicum.utils.EntityCheckService;
import ru.practicum.utils.enums.State;
import ru.practicum.utils.enums.StateAction;
import ru.practicum.utils.enums.Status;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.Utils.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;
    private final CategoryService categoryService;
    private final EntityCheckService checkService;
//    private final StatsClient statsClient;
    private final StatsRecordingService statsClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public Event create(Long userId, Event event, Long categoryId, LocationDto locationDto) {
        User user = checkService.getUserOrNotFound(userId);
        Location location = locationRepository.save(LocationMapper.toLocation(locationDto));
        Category category = categoryService.getById(categoryId);
        event.setInitiator(user);
        event.setLocation(location);
        event.setCategory(category);
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllByInitiator(Long userId, Integer from, Integer size) {
        checkService.checkUser(userId);
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return eventRepository.findByInitiatorId(userId, pageRequest);
    }

    @Override
    public Event getByInitiator(Long userId, Long eventId) {
        checkService.checkUser(userId);
        checkService.checkEvent(eventId);
        return eventRepository.findByInitiatorIdAndId(userId, eventId);
    }

    @Override
    @Transactional
    public Event updateByInitiator(EventUpdateDto eventUpdateDto, Long userId, Long eventId) {
        User user = checkService.getUserOrNotFound(userId);
        Event event = getOrNotFound(eventId);
        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new ConflictException(String.format(
                    "Пользователь %s не является инициатором события: %s",userId, eventId));
        }
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException(String.format(
                    "Пользователь не может обновить уже опублиованное событие: %s", eventId));
        }
        return updateEvent(event, eventUpdateDto);
    }

    @Override
    @Transactional
    public Event updateByAdmin(EventUpdateDto eventUpdateDto, Long eventId) {
        Event event = getOrNotFound(eventId);
        if (eventUpdateDto.getStateAction() != null) {
            if (eventUpdateDto.getStateAction().equals(StateAction.PUBLISH_EVENT)) {

                if (!event.getState().equals(State.PENDING)) {
                    throw new ConflictException(
                            String.format("Событие %s нельзя опубликовать повторно", event.getTitle()));
                }
                event.setPublishedOn(LocalDateTime.now());
                event.setState(State.PUBLISHED);
            } else {
                if (!event.getState().equals(State.PENDING)) {
                    throw new ConflictException(String.format(
                            "Событие %s в статусе отличном от PENDING, не можем быть отменено", event.getTitle()));
                }
                event.setState(State.CANCELED);
            }
        }
        return updateEvent(event, eventUpdateDto);
    }

    @Override
    public List<Event> getByAdmin(List<Long> users, List<String> states, List<Long> categories,
                                  String rangeStart, String rangeEnd, Integer from, Integer size) {

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        if (!rangeStart.isBlank()) {
            startTime = LocalDateTime.parse(rangeStart, STATS_FORMATTER);
        }
        if (!rangeEnd.isBlank()) {
            endTime = LocalDateTime.parse(rangeStart, STATS_FORMATTER);
        }
        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new ValidationException("Start must be after End");
            }
        }

        List<State> statesValue = new ArrayList<>();
        if (states != null) {
            for (String state : states) {
                statesValue.add(State.getStateValue(state));
            }
        }

        PageRequest pageRequest = PageRequest.of(from / size, size);
        return eventRepository.findEventsByAdminFromParam(
                users, statesValue, categories,  startTime, endTime, pageRequest);
    }

    @Override
    public Event getById(Long eventId, HttpServletRequest request) {
        Event event = getOrNotFound(eventId);
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new DataNotFoundException(String.format("Запрашиваемое событие %s не опубликовано", eventId));
        }
        sendInfo(request);
        event.setViews(getEventViewsById(event));
        eventRepository.save(event);
        return event;
    }

    @Override
    public List<Event> getByPublic(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd,
                                   Boolean onlyAvailable, String sort, Integer from, Integer size,
                                   HttpServletRequest request) {

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        if (!rangeStart.isBlank()) {
            startTime = LocalDateTime.parse(rangeStart, STATS_FORMATTER);
        }
        if (!rangeEnd.isBlank()) {
            endTime = LocalDateTime.parse(rangeStart, STATS_FORMATTER);
        }
        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new ValidationException("Start must be after End");
            }
        }
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findEventsByPublicFromParam(
                text, categories, paid, startTime, endTime, onlyAvailable, sort, pageRequest);

        sendInfo(request);
        for (Event event : events) {
            event.setViews(getEventViewsById(event));
            eventRepository.save(event);
        }
        return events;
    }

    @Override
    public List<Request> getRequestsForEventByInitiator(Long eventId, Long userId) {
        User user = checkService.getUserOrNotFound(userId);
        Event event = getOrNotFound(eventId);
        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new ConflictException(String.format(
                    "Пользователь с id: %s не инициатор события c id: %s",userId, eventId));
        }
        return requestRepository.findByEventId(eventId);
    }

    @Override
    @Transactional
    public RequestUpdateDtoResult updateStatusRequestsForEventByInitiator(
            RequestUpdateDtoRequest requestDto, Long eventId, Long userId) {
        User user = checkService.getUserOrNotFound(userId);
        Event event = getOrNotFound(eventId);
        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new ConflictException(String.format(
                    "Пользователь с id: %s не инициатор события c id: %s",userId, eventId));
        }
        RequestUpdateDtoResult result = RequestUpdateDtoResult.builder()
                .confirmedRequests(Collections.emptyList())
                .rejectedRequests(Collections.emptyList())
                .build();
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            return result;
        }

        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("Превышен лимит количества участников");
        }

        List<Request> confirmedRequests = new ArrayList<>();
        List<Request> rejectedRequests = new ArrayList<>();

        long vacantPlace = event.getParticipantLimit() - event.getConfirmedRequests();

        List<Request> requestsList = requestRepository.findAllById(requestDto.getRequestIds());

        for (Request request : requestsList) {
            if (!request.getStatus().equals(Status.PENDING)) {
                throw new ConflictException(String.format("Запрос %s должен быть в статусе PENDING",request.getId()));
            }
            if (requestDto.getStatus().equals(Status.CONFIRMED) && vacantPlace > 0) {
                request.setStatus(Status.CONFIRMED);
                event.setConfirmedRequests(requestRepository.countAllByEventIdAndStatus(eventId, Status.CONFIRMED));
                confirmedRequests.add(request);
                vacantPlace--;
            } else {
                request.setStatus(Status.REJECTED);
                rejectedRequests.add(request);
            }
        }
        result.setConfirmedRequests(confirmedRequests.stream()
                .map(RequestMapper::toRequestDto).collect(Collectors.toList()));
        result.setRejectedRequests(rejectedRequests.stream()
                .map(RequestMapper::toRequestDto).collect(Collectors.toList()));

        eventRepository.save(event);
        requestRepository.saveAll(requestsList);

        return result;
    }

    private Event getOrNotFound(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException(
                        String.format("Не найдено событие c id: %s", eventId)));
    }

    private Event updateEvent(Event event, EventUpdateDto eventUpdateDto) {
        if (eventUpdateDto.getAnnotation() != null && !eventUpdateDto.getAnnotation().isBlank()) {
            event.setAnnotation(eventUpdateDto.getAnnotation());
        }
        if (eventUpdateDto.getTitle() != null && !eventUpdateDto.getTitle().isBlank()) {
            event.setTitle(eventUpdateDto.getTitle());
        }
        if (eventUpdateDto.getDescription() != null && !eventUpdateDto.getDescription().isBlank()) {
            event.setDescription(eventUpdateDto.getDescription());
        }
        if (eventUpdateDto.getCategory() != null) {
            event.setCategory(categoryService.getById(eventUpdateDto.getCategory()));
        }
        if (eventUpdateDto.getLocation() != null) {
            event.setLocation(LocationMapper.toLocation(eventUpdateDto.getLocation()));
            locationRepository.save(event.getLocation());
        }
        if (eventUpdateDto.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(eventUpdateDto.getEventDate(), STATS_FORMATTER));
            locationRepository.save(event.getLocation());
        }
        Optional.ofNullable(eventUpdateDto.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(eventUpdateDto.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(eventUpdateDto.getRequestModeration()).ifPresent(event::setRequestModeration);

        if (eventUpdateDto.getStateAction() != null) {
            if (eventUpdateDto.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else if (eventUpdateDto.getStateAction() == StateAction.REJECT_EVENT ||
                    eventUpdateDto.getStateAction() == StateAction.CANCEL_REVIEW) {
                event.setState(State.CANCELED);
            } else if (eventUpdateDto.getStateAction() == StateAction.SEND_TO_REVIEW) {
                event.setState(State.PENDING);
            }
        }
        return eventRepository.save(event);
    }

    private void sendInfo(HttpServletRequest request) {
        HitDto hitDto = HitDto.builder()
                .app("main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().toString())
                .build();
        statsClient.addHit(hitDto);
    }

//    private Long getEventViewsById(HttpServletRequest request) {
//        List<String> uri = List.of(request.getRequestURI());
//        ResponseEntity<List<StatsDto>> response = statsClient.findStats(START_HISTORY, LocalDateTime.now(), uri, true);
//        List<StatsDto> result = objectMapper.convertValue(response.getBody(), new TypeReference<>() {});
//
//        if (result.isEmpty()) {
//            return 0L;
//        } else {
//            return result.get(0).getHits();
//        }
//    }

    private Long getEventViewsById(Event event) {
        LocalDateTime start = event.getPublishedOn();
        String uri = "/events/" + event.getId();
        StatsParamsDto statsParamsDto = new StatsParamsDto(start, LocalDateTime.now(), List.of(uri), true);

        List<StatsDto> result = statsClient.getStats(statsParamsDto);

        if (result.isEmpty()) {
            return 0L;
        } else {
            return result.get(0).getHits();
        }
    }

}
