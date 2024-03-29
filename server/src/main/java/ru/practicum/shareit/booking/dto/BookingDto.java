package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.Status;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Builder
public class BookingDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private Long itemId;
    private final Status status = Status.WAITING;
}
