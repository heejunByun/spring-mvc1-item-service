package com.itemservice.domain.item;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();


    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 1);
        //when
        Item savedItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        //given
        Item itemA = new Item("itemA", 10000, 1);
        Item itemB = new Item("itemB", 20000, 2);
        Item itemC = new Item("itemC", 30000, 3);

        itemRepository.save(itemA);
        itemRepository.save(itemB);
        itemRepository.save(itemC);
        //when
        List<Item> result = itemRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).contains(itemA, itemB, itemC);
    }

    @Test
    void updateItem() {
        //given
        Item itemA = new Item("itemA", 10000, 1);
        Item savedItem = itemRepository.save(itemA);
        Long itemId = savedItem.getId();
        //when
        Item updateItem = new Item("itemUpdate", 20000, 3);
        itemRepository.update(itemId, updateItem);
        //then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateItem.getQuantity());

    }

}