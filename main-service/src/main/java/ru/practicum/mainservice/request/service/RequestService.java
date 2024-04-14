package ru.practicum.mainservice.request.service;

import ru.practicum.mainservice.request.model.Request;

import java.util.List;

public interface RequestService {

    Request addRequest(Long userId, Long eventId);

    List<Request> getRequestsByUserId(Long userId);

    Request cancelRequest(Long userId, Long requestId);

}
