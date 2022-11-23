package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemListDto;
import ru.practicum.shareit.user.UserDbStorage;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemServiceImpl implements ItemService {
    private final ItemDbStorage items;
    private final UserDbStorage users;

    private final ItemMapper mapper;

    @Override
    public ItemDto createItem(ItemDto item, Long userId) throws ResponseStatusException {
        User user = users.getUserById(userId);
        return mapper.mapToItemDto(items.createItem(mapper.mapToItem(item), user));
    }

    @Override
    public ItemDto updateItem(Long itemId, Long userId, ItemDto item) {
        User user = users.getUserById(userId);
        return mapper.mapToItemDto(items.updateItem(itemId, user, mapper.mapToItem(item)));
    }

    @Override
    public ItemDto getItemByItemId(Long itemId) {
        return mapper.mapToItemDto(items.getItemByItemId(itemId));
    }

    @Override
    public ItemListDto getPersonalItems(Long userId) {
        return ItemListDto.builder()
                .items(items.getPersonalItems(userId).stream().map(mapper::mapToItemDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public ItemListDto getFoundItems(String text) {
        if (text.isBlank()) {
            return ItemListDto.builder().items(new ArrayList<>()).build();
        }
        return ItemListDto.builder()
                .items(items.getFoundItems(text).stream().map(mapper::mapToItemDto).collect(Collectors.toList()))
                .build();
    }
}