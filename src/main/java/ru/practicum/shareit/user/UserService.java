package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(Long id);

    List<User> getUsers();

    User updateUser(User user, Long userId);

    void deleteUser(Long id);
}
