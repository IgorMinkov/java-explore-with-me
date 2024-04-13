package ru.practicum.mainservice.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.mainservice.category.CategoryRepository;
import ru.practicum.mainservice.event.repository.EventRepository;
import ru.practicum.mainservice.event.repository.LocationRepository;
import ru.practicum.mainservice.exception.DataNotFoundException;
import ru.practicum.mainservice.user.UserRepository;

@Component
@AllArgsConstructor
public class EntityCheckService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public void checkUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new DataNotFoundException(String.format("Не найден пользователь c id: %s", id));
        }
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

    public void checkLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new DataNotFoundException(String.format("Не найдена локация c id: %s", id));
        }
    }

}
