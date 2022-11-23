package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserDbStorage users;

    @Override
    public UserDto createUser(UserDto user) {
        return UserMapper.mapper.mapToUserDto(users.createUser(UserMapper.mapper.mapToUser(user)));
    }

    @Override
    public UserDto getUserById(Long id) {
        return UserMapper.mapper.mapToUserDto(users.getUserById(id));
    }

    @Override
    public List<UserDto> getUsers() {
        return users.getUsers().stream().map(UserMapper.mapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user, Long userId) {
        return UserMapper.mapper.mapToUserDto(users.updateUser(UserMapper.mapper.mapToUser(user), userId));
    }

    @Override
    public void deleteUser(Long id) {
        users.deleteUser(id);
    }
}
