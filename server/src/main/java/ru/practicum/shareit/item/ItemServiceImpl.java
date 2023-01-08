package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemServiceImpl implements ItemService {
    private final ItemRepository items;
    private final UserRepository users;
    private final BookingRepository bookings;
    private final CommentRepository comments;
    private final ItemMapper mapper;
    private final ItemRequestRepository itemRequests;

    @Override
    @Transactional
    public ItemDtoResponse createItem(ItemDto item, Long userId) throws ResponseStatusException {
        Item newItem = mapper.mapToItemFromItemDto(item);
        if (item.getRequestId() != null) {
            ItemRequest itemRequest = itemRequests.findById(item.getRequestId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Запроса с id=" +
                            item.getRequestId() + " нет"));
            newItem.setRequest(itemRequest);
        }
        newItem.setOwner(users.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователя с id=" + userId + " нет")));
        return mapper.mapToItemDtoResponse(items.save(newItem));
    }

    @Override
    @Transactional
    public ItemDtoResponse updateItem(Long itemId, Long userId, ItemDtoUpdate item) {
        Item updateItem = items.findById(itemId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Предмета с id=" + itemId + " нет"));
        if (!updateItem.getOwner().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Предмет с id=" + itemId + " пользователю с id=" + userId + " не пренадлежит");
        }
        return mapper.mapToItemDtoResponse(items.save(mapper.mapToItemFromItemDtoUpdate(item, updateItem)));
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDtoResponse getItemByItemId(Long userId, Long itemId) {
        Item item = items.findById(itemId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Предмета с id=" + itemId + " нет"));
        ItemDtoResponse itemDtoResponse = mapper.mapToItemDtoResponse(item);
        if (item.getOwner().getId().equals(userId)) {
            itemDtoResponse.setLastBooking(mapper
                    .mapToBookingShortDto(bookings
                            .findFirstByItemAndStatusIsOrderByStartAsc(item, Status.APPROVED)
                    ));
            itemDtoResponse.setNextBooking(mapper.mapToBookingShortDto(bookings
                    .findFirstByItemAndStatusIsOrderByEndDesc(item, Status.APPROVED)));
            return itemDtoResponse;
        }
        return itemDtoResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemListDto getPersonalItems(Pageable pageable, Long userId) {
        if (!users.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователя с id=" + userId + " не существует");
        }
        List <Item> findItems = items.findAllByOwnerId(pageable, userId);
        List<ItemDtoResponse> personalItems = new ArrayList<>();
        for (Item item : findItems) {
            ItemDtoResponse itemDtoResponse = mapper.mapToItemDtoResponse(item);
            itemDtoResponse.setLastBooking(mapper.mapToBookingShortDto(
                    bookings.findFirstByItemAndStatusIsOrderByStartAsc(item, Status.APPROVED)));
            itemDtoResponse.setNextBooking(mapper.mapToBookingShortDto(
                    bookings.findFirstByItemAndStatusIsOrderByEndDesc(item, Status.APPROVED)));
            personalItems.add(itemDtoResponse);

        }
        return ItemListDto.builder().items(personalItems).build();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemListDto getFoundItems(Pageable pageable, String text) {
        if (text.isBlank()) {
            return ItemListDto.builder().items(new ArrayList<>()).build();
        }
        return ItemListDto.builder()
                .items(items.findAllByNameOrDescriptionContainingIgnoreCaseAndAvailableTrue(pageable, text, text).stream()
                        .map(mapper::mapToItemDtoResponse).collect(Collectors.toList())).build();
    }

    @Override
    @Transactional
    public CommentDtoResponse addComment(Long itemId, Long userId, CommentDto commentDto) {
        if (!bookings.existsBookingByItemIdAndBookerIdAndStatusAndEndIsBefore(itemId, userId,
                Status.APPROVED, LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У пользователя с id="
                    + userId + " небыло ниодной брони на предмет с id=" + itemId);
        } else {
            User author = users.findById(userId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователя с id=" + userId + " нет"));
            Item item = items.findById(itemId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Предмета с id=" + itemId + " нет"));
            Comment comment = mapper.mapToCommentFromCommentDto(commentDto);
            comment.setItem(item);
            comment.setAuthor(author);
            comment.setCreated(LocalDateTime.now());
            return mapper.mapToCommentDtoResponseFromComment(comments.save(comment));
        }
    }
}