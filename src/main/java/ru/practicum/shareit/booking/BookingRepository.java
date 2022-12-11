package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
            Long bookerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByBookerIdAndEndIsBeforeOrderByStartDesc(Long bookerId, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStartIsAfterOrderByStartDesc(Long bookerId, LocalDateTime start);

    List<Booking> findAllByBookerIdAndStatusIsOrderByStartDesc(Long bookerId, Status status);

    List<Booking> findAllByItemIdInOrderByStartDesc(Collection<Long> itemId);

    List<Booking> findAllByItemIdInAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
            Collection<Long> itemId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItemIdInAndEndIsBeforeOrderByStartDesc(Collection<Long> itemId, LocalDateTime end);

    List<Booking> findAllByItemIdInAndStartIsAfterOrderByStartDesc(Collection<Long> itemId, LocalDateTime start);

    List<Booking> findAllByItemIdInAndStatusIsOrderByStartDesc(Collection<Long> itemId, Status status);

    @Query(value = "SELECT * FROM bookings WHERE (item_id = ?1 AND start_date < NOW()) " +
            "ORDER BY start_date DESC LIMIT 1", nativeQuery = true)
    Booking findLastBooking(Long itemId);

    @Query(value = "SELECT * FROM bookings WHERE (item_id = ?1 AND start_date > NOW()) " +
            "ORDER BY start_date ASC LIMIT 1", nativeQuery = true)
    Booking findNextBooking(Long itemId);

    Boolean existsBookingByItemIdAndBookerIdAndStatusAndEndIsBefore(
            Long itemId, Long bookerId, Status status, LocalDateTime end);
}
