package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemListDto;


public interface ItemService {
    ItemDto createItem(ItemDto item, Long userId);

    ItemDto updateItem(Long itemId, Long userId, ItemDto item);

    ItemDto getItemByItemId(Long itemId);

    ItemListDto getPersonalItems(Long userId);

    ItemListDto getFoundItems(String text);
}
