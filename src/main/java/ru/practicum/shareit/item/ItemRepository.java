package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOwnerId(Long ownerId);

    List<Item> findAllByNameOrDescriptionContainingIgnoreCaseAndAvailableTrue(String name, String description);

    Boolean existsItemByOwnerId(Long ownerId);

    @Query(value = "SELECT item_id FROM items WHERE owner_id = ?1", nativeQuery = true)
    List<Long> findAllItemIdByOwnerId(Long ownerId);
}
