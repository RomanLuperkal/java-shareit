package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto item, Long userId);

    ItemDto updateItem(Long itemId, Long userId, ItemDto item);

    ItemDto getItemByItemId(Long itemId);

    List<ItemDto> getPersonalItems(Long userId);

    List<ItemDto> getFoundItems(String text);
}
