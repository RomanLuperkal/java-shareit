package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.client.BaseWebClient;
import ru.practicum.shareit.handler.exception.StateException;

import java.util.Map;

@Service
public class BookingClient extends BaseWebClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String url) {
        super(WebClient.builder()
                .baseUrl(url + API_PREFIX)
                .build());
    }

    public Mono<ResponseEntity<Object>> createBooking(Long bookerId, BookingDto bookingDto) {
        return post("", bookerId, bookingDto);
    }

    public Mono<ResponseEntity<Object>> approveBooking(Long ownerId, String approved, Long bookingId) {
        Map<String, Object> parameters = Map.of(
                "approved", approved
        );
        return patch("/" + bookingId + "?approved={approved}", ownerId, parameters, null);
    }

    public Mono<ResponseEntity<Object>> getBookingByIdForOwnerAndBooker(Long bookingId, Long userId) {
        return get("/" + bookingId, userId);
    }

    public Mono<ResponseEntity<Object>> getAllBookingsForUser(Long userId, String state, Integer from,
                                                              Integer size) {
        validateState(state);
        Map<String, Object> parameters = Map.of(
                "state", state,
                "from", from,
                "size", size
        );
        return get("?state={state}&&from={from}&&size={size}", userId, parameters);
    }

    public Mono<ResponseEntity<Object>> getAllBookingsForItemsUser(Long userId, String state, Integer from,
                                                                   Integer size) {
        validateState(state);
        Map<String, Object> parameters = Map.of(
                "state", state,
                "from", from,
                "size", size
        );
        return get("/owner?state={state}&&from={from}&&size={size}", userId, parameters);
    }

    private void validateState(String state) {
        if (State.fromValue(state).equals(State.UNSUPPORTED_STATUS)) {
            throw new StateException("Unknown state: " + state);
        }
    }
}
