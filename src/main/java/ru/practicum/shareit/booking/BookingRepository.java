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
            Long booker_id, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByBookerIdAndEndIsBeforeOrderByStartDesc(Long booker_id, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStartIsAfterOrderByStartDesc(Long booker_id, LocalDateTime start);

    List<Booking> findAllByBookerIdAndStatusIsOrderByStartDesc(Long booker_id, Status status);

    List<Booking> findAllByItemIdInOrderByStartDesc(Collection<Long> item_id);

    List<Booking> findAllByItemIdInAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
            Collection<Long> item_id, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItemIdInAndEndIsBeforeOrderByStartDesc(Collection<Long> item_id, LocalDateTime end);

    List<Booking> findAllByItemIdInAndStartIsAfterOrderByStartDesc(Collection<Long> item_id, LocalDateTime start);

    List<Booking> findAllByItemIdInAndStatusIsOrderByStartDesc(Collection<Long> item_id, Status status);

    @Query(value = "SELECT * FROM bookings WHERE (item_id = ?1 AND start_date < NOW()) " +
            "ORDER BY start_date DESC LIMIT 1", nativeQuery = true)
    Booking findLastBooking(Long itemId);

    @Query(value = "SELECT * FROM bookings WHERE (item_id = ?1 AND start_date > NOW()) " +
            "ORDER BY start_date ASC LIMIT 1", nativeQuery = true)
    Booking findNextBooking(Long itemId);

    Boolean existsBookingByItemIdAndBookerIdAndStatusAndEndIsBefore(
            Long item_id, Long booker_id, Status status, LocalDateTime end);
}
