package ru.practicum.mainservice.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.event.model.event.Event;
import ru.practicum.mainservice.event.repository.EventRepository;
import ru.practicum.mainservice.exception.DataNotFoundException;
import ru.practicum.mainservice.exception.handler.ConflictException;
import ru.practicum.mainservice.request.RequestRepository;
import ru.practicum.mainservice.request.model.Request;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.utils.EntityCheckService;
import ru.practicum.mainservice.utils.enums.State;
import ru.practicum.mainservice.utils.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final EntityCheckService checkService;

    @Override
    public Request create(Long userId, Long eventId) {
        User user = checkService.getUserOrNotFound(userId);
        Event event = checkService.getEventOrNotFound(eventId);

        if (event.getParticipantLimit() <= event.getConfirmedRequests() && event.getParticipantLimit() != 0) {
            throw new ConflictException(String.format("Число запросов для события %s превысило лимит", event));
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
                request = requestRepository.save(request);
                event.setConfirmedRequests(requestRepository.countAllByEventIdAndStatus(eventId, Status.CONFIRMED));
                eventRepository.save(event);
                return request;
            }
            request = requestRepository.save(request);
            return request;
        }
    }

    @Override
    public List<Request> getListByUserId(Long userId) {
        checkService.checkUser(userId);
        return requestRepository.findByRequesterId(userId);
    }

    @Override
    public Request cancelRequest(Long userId, Long requestId) {
        checkService.checkUser(userId);
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Не найден запрос c id: %s", requestId)));
        request.setStatus(Status.CANCELED);

        return requestRepository.save(request);
    }

}
