package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;
import ru.practicum.utils.EntityCheckService;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final EntityCheckService checkService;

    @Override
    @Transactional
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        checkService.checkUser(userId);
        repository.deleteById(userId);
    }

    @Override
    public List<User> getList(List<Long> ids, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);

        if (ids == null) {
            return repository.findAll(pageRequest).toList();
        } else {
            return repository.findByIdInOrderByIdAsc(ids, pageRequest);
        }
    }

}
