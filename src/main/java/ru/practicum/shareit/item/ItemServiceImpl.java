package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserDbStorage;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemServiceImpl implements ItemService {
    private final ItemDbStorage items;
    private final UserDbStorage users;

    @Override
    public Item createItem(Item item, Long userId) throws ResponseStatusException {
        User user = users.getUserById(userId);
        return items.createItem(item, user);
    }

    @Override
    public Item updateItem(Long itemId, Long userId, Item item) {
        User user = users.getUserById(userId);
        return items.updateItem(itemId, user, item);
    }

    @Override
    public Item getItemByItemId(Long itemId) {
        return items.getItemByItemId(itemId);
    }

    @Override
    public List<Item> getPersonalItems(Long userId) {
        return items.getPersonalItems(userId);
    }

    @Override
    public List<Item> getFoundItems(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return items.getFoundItems(text);
    }
}
