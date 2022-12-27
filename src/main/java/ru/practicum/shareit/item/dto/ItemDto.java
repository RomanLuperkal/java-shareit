package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {
    @Pattern(regexp = "^[^ ].*[^ ]$", message = "Неккоректное имя")
    @Size(max = 255)
    @NotNull(message = "Поле name обязательно")
    private String name;
    @Pattern(regexp = "^[^ ].*[^ ]$", message = "Неккоректное описание")
    @Size(max = 500)
    @NotNull(message = "Поле description обязательно")
    private String description;
    @NotNull(message = "Поле available обязательно")
    private Boolean available;
    @Min(1)
    private Long requestId;
}
