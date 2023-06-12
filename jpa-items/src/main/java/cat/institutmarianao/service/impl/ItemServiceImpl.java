package cat.institutmarianao.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cat.institutmarianao.domain.Item;
import cat.institutmarianao.service.ItemService;

@Stateless
public class ItemServiceImpl implements ItemService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void create(Item item) {
		entityManager.persist(item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getAllItems() {
		return entityManager.createQuery("select u from Item u ").getResultList();
	}

	@Override
	public Item getByReference(String reference) {
		return entityManager.find(Item.class, reference);
	}

	@Override
	public List<Item> findItemsByPriceBetween(Double minPrice, Double maxPrice) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Item> query = builder.createQuery(Item.class);
		Root<Item> items = query.from(Item.class);
		Predicate predicateBetween = builder.between(items.get("price"), minPrice, maxPrice);
		query.select(items).where(predicateBetween);
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<Item> findItemsByStockLessThan(int stock) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Item> query = builder.createQuery(Item.class);
		Root<Item> items = query.from(Item.class);
		Predicate predicateBetween = builder.lessThan(items.get("stock"), stock);
		query.select(items).where(predicateBetween);
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public Item edit(Item item) {
		return entityManager.merge(item);
	}

	@Override
	public void remove(Item item) {
		entityManager.remove(entityManager.merge(item));
	}
}
