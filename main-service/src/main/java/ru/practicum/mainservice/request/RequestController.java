package ru.practicum.mainservice.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.request.dto.RequestDto;
import ru.practicum.mainservice.request.model.Request;
import ru.practicum.mainservice.request.model.RequestMapper;
import ru.practicum.mainservice.request.service.RequestService;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public RequestDto addRequest(@Positive @PathVariable Long userId,
                                 @Positive @RequestParam Long eventId) {

        Request request = requestService.addRequest(userId, eventId);
        log.info("Пользователь с id {} добавил запрос на событие {}", userId, eventId);
        return RequestMapper.toRequestDto(request);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<RequestDto> getRequestsByUserId(@Positive @PathVariable Long userId) {

        List<Request> result = requestService.getRequestsByUserId(userId);
        log.info("Поулчение списка всех запросов пользователя c id: {}", userId);
        return result.stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(value = HttpStatus.OK)
    public RequestDto cancelRequest(@Positive @PathVariable Long userId, @Positive @PathVariable Long requestId) {

        Request request = requestService.cancelRequest(userId, requestId);
        log.info("Пользователь с id: {} отменил запрос c id: {}", userId, requestId);
        return RequestMapper.toRequestDto(request);
    }

}
