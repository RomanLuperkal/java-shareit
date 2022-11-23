package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserDbStorage;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemServiceImpl implements ItemService {
    private final ItemDbStorage items;
    private final UserDbStorage users;

    @Override
    public ItemDto createItem(ItemDto item, Long userId) throws ResponseStatusException {
        User user = users.getUserById(userId);
        return ItemMapper.mapper.mapToItemDto(items.createItem(ItemMapper.mapper.mapToItem(item), user));
    }

    @Override
    public ItemDto updateItem(Long itemId, Long userId, ItemDto item) {
        User user = users.getUserById(userId);
        return ItemMapper.mapper.mapToItemDto(items.updateItem(itemId, user, ItemMapper.mapper.mapToItem(item)));
    }

    @Override
    public ItemDto getItemByItemId(Long itemId) {
        return ItemMapper.mapper.mapToItemDto(items.getItemByItemId(itemId));
    }

    @Override
    public List<ItemDto> getPersonalItems(Long userId) {
        return items.getPersonalItems(userId).stream().map(ItemMapper.mapper::mapToItemDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getFoundItems(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return items.getFoundItems(text).stream().map(ItemMapper.mapper::mapToItemDto).collect(Collectors.toList());
    }
}