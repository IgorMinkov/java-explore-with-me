package ru.practicum.user.service;

import ru.practicum.user.model.User;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(Long userId);

    List<User> getList(List<Long> ids, Integer from, Integer size);

}
