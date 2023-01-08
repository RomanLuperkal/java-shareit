package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping("/bookings")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookingController {

    private final BookingClient bookingClient;

    @PostMapping
    public Mono<ResponseEntity<Object>> createBooking(@RequestHeader("X-Sharer-User-Id") @Min(1) Long bookerId,
                                                      @Valid @RequestBody BookingDto bookingDto) {
        return bookingClient.createBooking(bookerId, bookingDto);
    }

    @PatchMapping("{bookingId}")
    public Mono<ResponseEntity<Object>> approveBooking(@RequestHeader("X-Sharer-User-Id") @Min(1) Long ownerId,
                                                       @RequestParam String approved,
                                                       @PathVariable @Min(1) Long bookingId) {
        return bookingClient.approveBooking(ownerId, approved, bookingId);
    }

    @GetMapping("{bookingId}")
    public Mono<ResponseEntity<Object>> getBookingByIdForOwnerAndBooker(
            @PathVariable @Min(1) Long bookingId,
            @RequestHeader("X-Sharer-User-Id") @Min(1) Long userId) {
        return bookingClient.getBookingByIdForOwnerAndBooker(bookingId, userId);
    }

    @GetMapping
    public Mono<ResponseEntity<Object>> getAllBookingsForUser(
            @RequestHeader("X-Sharer-User-Id") @Min(1) Long userId,
            @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(20) Integer size) {
        return bookingClient.getAllBookingsForUser(userId, state, from, size);
    }

    @GetMapping("owner")
    public Mono<ResponseEntity<Object>> getAllBookingsForItemsUser(
            @RequestHeader("X-Sharer-User-Id") @Min(1) Long userId,
            @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(20) Integer size) {
        return bookingClient.getAllBookingsForItemsUser(userId, state, from, size);
    }
}
