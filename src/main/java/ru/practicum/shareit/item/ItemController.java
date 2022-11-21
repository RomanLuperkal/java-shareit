package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.mapper.Mapper;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                                              @Valid @RequestBody ItemDto itemDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Mapper.mapToItemDto(itemService.createItem(Mapper.mapToItem(itemDto), userId)));
    }

    @PatchMapping("{itemId}")
    public ResponseEntity<ItemDto> updateItem(@RequestHeader("X-Sharer-User-Id") @Min(1) Long userId,
                                              @RequestBody ItemDto itemDto,
                                              @PathVariable Long itemId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Mapper.mapToItemDto(itemService.updateItem(itemId, userId, Mapper.mapToItem(itemDto))));
    }

    @GetMapping("{itemId}")
    public ResponseEntity<ItemDto> getItemByItemId(@PathVariable Long itemId) {
        return ResponseEntity.status(HttpStatus.OK).body(Mapper.mapToItemDto(itemService.getItemByItemId(itemId)));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getPersonalItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.getPersonalItems(userId).stream()
                        .map(Mapper::mapToItemDto).collect(Collectors.toList()));
    }

    @GetMapping("search")
    public ResponseEntity<List<ItemDto>> getFoundItems(@RequestParam String text) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemService.getFoundItems(text).stream().map(Mapper::mapToItemDto).collect(Collectors.toList()));
    }

}
