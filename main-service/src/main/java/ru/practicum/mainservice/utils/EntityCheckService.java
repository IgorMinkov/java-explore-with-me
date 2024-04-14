package ru.practicum.mainservice.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.mainservice.category.CategoryRepository;
import ru.practicum.mainservice.compilation.CompilationRepository;
import ru.practicum.mainservice.event.model.event.Event;
import ru.practicum.mainservice.event.repository.EventRepository;
import ru.practicum.mainservice.event.repository.LocationRepository;
import ru.practicum.mainservice.exception.DataNotFoundException;
import ru.practicum.mainservice.request.RequestRepository;
import ru.practicum.mainservice.user.UserRepository;
import ru.practicum.mainservice.user.model.User;

@Component
@AllArgsConstructor
public class EntityCheckService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final CompilationRepository compilationRepository;

    public void checkUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new DataNotFoundException(String.format("Не найден пользователь c id: %s", id));
        }
    }

    public User getUserOrNotFound(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Не найден пользователь c id: %s", id)));
    }

    public void checkCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new DataNotFoundException(String.format("Не найдена категория c id: %s", id));
        }
    }

    public void checkEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new DataNotFoundException(String.format("Не найдено событие c id: %s", id));
        }
    }

    public Event getEventOrNotFound(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Не найдено событие c id: %s", id)));
    }

    public void checkLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new DataNotFoundException(String.format("Не найдена локация c id: %s", id));
        }
    }

    public void checkRequest(Long id) {
        if (!requestRepository.existsById(id)) {
            throw new DataNotFoundException(String.format("Не найден запрос c id: %s", id));
        }
    }

    public void checkCompilation(Long id) {
        if (!compilationRepository.existsById(id)) {
            throw new DataNotFoundException(String.format("Не найдена подборка c id: %s", id));
        }
    }

}
