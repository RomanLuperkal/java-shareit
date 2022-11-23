package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserListDto;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto getUserById(Long id);

    UserListDto getUsers();

    UserDto updateUser(UserDto userDto, Long userId);

    void deleteUser(Long id);
}
