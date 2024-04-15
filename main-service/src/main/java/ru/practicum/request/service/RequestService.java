package ru.practicum.request.service;

import ru.practicum.request.model.Request;

import java.util.List;

public interface RequestService {

    Request create(Long userId, Long eventId);

    List<Request> getListByUserId(Long userId);

    Request cancelRequest(Long userId, Long requestId);

}
