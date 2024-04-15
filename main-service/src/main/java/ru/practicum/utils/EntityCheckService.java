package ru.practicum.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.category.CategoryRepository;
import ru.practicum.compilation.CompilationRepository;
import ru.practicum.event.model.event.Event;
import ru.practicum.exception.DataNotFoundException;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

@Component
@AllArgsConstructor
public class EntityCheckService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
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

    public void checkCompilation(Long id) {
        if (!compilationRepository.existsById(id)) {
            throw new DataNotFoundException(String.format("Не найдена подборка c id: %s", id));
        }
    }

}
