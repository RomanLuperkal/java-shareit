package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserDbStorage users;

    @Override
    public User createUser(User user) {
        return users.createUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return users.getUserById(id);
    }

    @Override
    public List<User> getUsers() {
        return users.getUsers();
    }

    @Override
    public User updateUser(User user, Long userId) {
        return users.updateUser(user, userId);
    }

    @Override
    public void deleteUser(Long id) {
        users.deleteUser(id);
    }
}
