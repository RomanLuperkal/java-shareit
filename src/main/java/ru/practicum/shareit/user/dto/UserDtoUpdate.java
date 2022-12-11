package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;

@Builder
@Getter
public class UserDtoUpdate {
    @Pattern(regexp = "^\\w+.*\\S$", message = "Неккоректное имя")
    @Size(max = 255)
    private String name;
    @Email(message = "Некорректный email")
    private String email;
}
