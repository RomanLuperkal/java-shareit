package ru.practicum.shareit.item;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.item.dto.*;


public interface ItemService {
    ItemDtoResponse createItem(ItemDto item, Long userId);

    ItemDtoResponse updateItem(Long itemId, Long userId, ItemDtoUpdate item);

    ItemDtoResponse getItemByItemId(Long userId, Long itemId);

    ItemListDto getPersonalItems(Pageable pageable, Long userId);

    ItemListDto getFoundItems(Pageable pageable, String text);

    CommentDtoResponse addComment(Long itemId, Long userId, CommentDto commentDto);
}
