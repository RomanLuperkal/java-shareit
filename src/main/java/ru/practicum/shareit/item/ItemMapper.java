package ru.practicum.shareit.item;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Mapper
public interface ItemMapper {
    ItemMapper mapper = Mappers.getMapper(ItemMapper.class);

    ItemDto mapToItemDto(Item item);

    Item mapToItem(ItemDto itemDto);

}
