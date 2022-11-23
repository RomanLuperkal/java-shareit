package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserListDto;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserDbStorage users;

    private final UserMapper mapper;

    @Override
    public UserDto createUser(UserDto user) {
        return mapper.mapToUserDto(users.createUser(mapper.mapToUser(user)));
    }

    @Override
    public UserDto getUserById(Long id) {
        return mapper.mapToUserDto(users.getUserById(id));
    }

    @Override
    public UserListDto getUsers() {
        return UserListDto.builder()
                .users(users.getUsers().stream().map(mapper::mapToUserDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public UserDto updateUser(UserDto user, Long userId) {
        return mapper.mapToUserDto(users.updateUser(mapper.mapToUser(user), userId));
    }

    @Override
    public void deleteUser(Long id) {
        users.deleteUser(id);
    }
}
