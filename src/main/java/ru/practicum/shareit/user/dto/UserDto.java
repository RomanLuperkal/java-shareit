package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
public class UserDto {
    @Pattern(regexp = "^\\w+.*\\S$", message = "Неккоректное имя")
    @Size(max = 255)
    private String name;
    @Email(message = "Некорректный email")
    @NotNull(message = "Поле email обязательно")
    private String email;
}
