package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static 사용 -> 실무에서는 concurrent
    private static long sequence = 0L; //static 사용 -> 실무에서는 atomic

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }
    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        // 감싼 이유 알기
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        // Item이 아니라 ItemDto 만들고 거기 id 제외한 itemName, price, quantity만 넣어주는게 나음.
        // ItemParamDto
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
