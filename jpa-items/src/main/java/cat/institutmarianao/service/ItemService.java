package cat.institutmarianao.service;

import java.util.List;

import javax.ejb.Local;

import cat.institutmarianao.domain.Item;

@Local
public interface ItemService {
	void create(Item item);

	List<Item> getAllItems();

	Item getByReference(String reference);

	List<Item> findItemsByPriceBetween(Double minPrice, Double maxPrice);

	List<Item> findItemsByStockLessThan(int stock);

	Item edit(Item item);

	void remove(Item item);
}
