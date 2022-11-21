package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item createItem(Item item, Long userId);

    Item updateItem(Long itemId, Long userId, Item item);

    Item getItemByItemId(Long itemId);

    List<Item> getPersonalItems(Long userId);

    List<Item> getFoundItems(String text);
}
