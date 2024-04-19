package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.DataNotFoundException;
import ru.practicum.event.model.event.Event;
import ru.practicum.exception.ConflictException;
import ru.practicum.request.RequestRepository;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;
import ru.practicum.utils.EntityCheckService;
import ru.practicum.utils.enums.State;
import ru.practicum.utils.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final EntityCheckService checkService;

    @Override
    @Transactional
    public Request create(Long userId, Long eventId) {
        User user = checkService.getUserOrNotFound(userId);
        Event event = checkService.getEventOrNotFound(eventId);

        if (event.getParticipantLimit() != 0) {
            if (Objects.equals(event.getConfirmedRequests(), event.getParticipantLimit())) {
                throw new ConflictException(String.format("Число запросов для события %s превысило лимит", event));
            }
        }
        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Автору события не нужно подавать запрос на участие в нем");
        }
        if (requestRepository.findByRequesterIdAndEventId(userId, eventId).isPresent()) {
            throw new ConflictException(String.format("Вы уже участвуете в событии: %s", event.getTitle()));
        }
        if (event.getState() != State.PUBLISHED) {
            throw new ConflictException(
                    String.format("Событие %s еще не опубликовано, запросы пока не принимаются", eventId));
        } else {
            Request request = Request.builder()
                    .requester(user)
                    .event(event)
                    .created(LocalDateTime.now())
                    .status(Status.PENDING)
                    .build();

            if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
                request.setStatus(Status.CONFIRMED);
                event.setConfirmedRequests(requestRepository.countAllByEventIdAndStatus(eventId, Status.CONFIRMED));
                eventRepository.save(event);
                return requestRepository.save(request);
            }
            return requestRepository.save(request);
        }
    }

    @Override
    public List<Request> getListByUserId(Long userId) {
        checkService.checkUser(userId);
        return requestRepository.findByRequesterId(userId);
    }

    @Override
    @Transactional
    public Request cancelRequest(Long userId, Long requestId) {
        checkService.checkUser(userId);
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Не найден запрос c id: %s", requestId)));
        request.setStatus(Status.CANCELED);

        return requestRepository.save(request);
    }

}
