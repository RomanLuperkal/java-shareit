package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto getUserById(Long id);

    List<UserDto> getUsers();

    UserDto updateUser(UserDto userDto, Long userId);

    void deleteUser(Long id);
}
