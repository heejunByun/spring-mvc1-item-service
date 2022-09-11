package com.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    /**
     * static 을 사용하지 않으면, spring 은 어차피 싱글톤으로 관리되기 때문에 크게 상관은 없지만
     * 다른 곳에서 ItemRepository 를 따로 new 로 객체를 생성하게되면, store 객체가 따로 생성될 수 있기 때문에 static 을 사용하자
     */
    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }


}
