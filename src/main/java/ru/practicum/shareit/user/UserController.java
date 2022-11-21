package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.mapper.Mapper;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Mapper.mapToUserDto(userService.createUser(Mapper.mapToUser(userDto))));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") @Min(1) Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(Mapper.mapToUserDto(userService.getUserById(userId)));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUsers().stream().map(Mapper::mapToUserDto).collect(Collectors.toList()));
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Mapper.mapToUserDto(userService.updateUser(Mapper.mapToUser(userDto), userId)));
    }

    @DeleteMapping("{id}")
    public void deleteUser(@Min(1) @PathVariable("id") Long userId) {
        userService.deleteUser(userId);
    }

}
