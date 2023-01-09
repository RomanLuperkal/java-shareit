package ru.practicum.shareit.booking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {
    List<Booking> findAllByBookerIdOrderByStartDesc(Pageable pageable, Long bookerId);

    List<Booking> findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
            Pageable pageable, Long bookerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByBookerIdAndEndIsBeforeOrderByStartDesc(Pageable pageable, Long bookerId, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStartIsAfterOrderByStartDesc(
            Pageable pageable, Long bookerId, LocalDateTime start);

    List<Booking> findAllByBookerIdAndStatusIsOrderByStartDesc(Pageable pageable, Long bookerId, Status status);

    List<Booking> findAllByItemIdInOrderByStartDesc(Pageable pageable, Collection<Long> itemId);

    List<Booking> findAllByItemIdInAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
            Pageable pageable, Collection<Long> itemId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItemIdInAndEndIsBeforeOrderByStartDesc(
            Pageable pageable, Collection<Long> itemId, LocalDateTime end);

    List<Booking> findAllByItemIdInAndStartIsAfterOrderByStartDesc(
            Pageable pageable, Collection<Long> itemId, LocalDateTime start);

    List<Booking> findAllByItemIdInAndStatusIsOrderByStartDesc(
            Pageable pageable, Collection<Long> itemId, Status status);

    Optional<Booking> findFirstByItemAndStatusIsOrderByStartAsc(Item item, Status status);

    Optional<Booking> findFirstByItemAndStatusIsOrderByEndDesc(Item item, Status status);

    Boolean existsBookingByItemIdAndBookerIdAndStatusAndEndIsBefore(
            Long itemId, Long bookerId, Status status, LocalDateTime end);
}
