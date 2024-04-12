package ru.practicum.mainservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.mainservice.exception.DataNotFoundException;
import ru.practicum.mainservice.user.UserRepository;

@Component
@RequiredArgsConstructor
public class EntityCheckService {

    private final UserRepository userRepository;

    public void checkUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new DataNotFoundException(String.format("Не найден пользователь c id: %s", id));
        }
    }

}
