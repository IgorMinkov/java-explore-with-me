package ru.practicum.mainservice.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.user.dto.UserDto;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.user.model.UserMapper;
import ru.practicum.mainservice.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        User user = userService.create(UserMapper.toUser(userDto));
        log.info("Добавлен пользователь: {} ", user);
        return UserMapper.toUserDto(user);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        List<User> result = userService.getList(ids, from, size);
        log.info("Получен список пользователей с id: {}", ids);
        return result.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@Positive @PathVariable("userId") Long userId) {
        userService.delete(userId);
        log.info("Пользователь c id: {} удалён", userId);
    }

}
