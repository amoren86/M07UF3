package cat.institutmarianao.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cat.institutmarianao.DBConnection;
import cat.institutmarianao.dao.Item;
import cat.institutmarianao.dao.ItemDAO;

public class ItemDAOTest {
	private DBConnection dBConnection;
	private String connectionProperties = "db_test.properties";
	private ItemDAO itemDAO;

	private static final double PRICE_ERROR_TOLERANCE = 0.0001;

	@Before
	public void setUp() {
		dBConnection = new DBConnection(connectionProperties);
		itemDAO = new ItemDAO(dBConnection);
	}

	@After
	public void tearDown() throws IOException, SQLException {
		itemDAO.getConnection().close();
	}

	@Test
	public void createItemOk() throws Exception {
		Item itemMOCK = new Item("MOCK-REFERENCE", "mock name", "mock description", 1234.56, "mock-image.jpg", 1);

		Item createdItem = itemDAO.createItem(itemMOCK.getReference(), itemMOCK.getName(), itemMOCK.getDescription(),
				itemMOCK.getPrice(), itemMOCK.getImage(), itemMOCK.getStock());

		Assert.assertNotNull(createdItem);
		Assert.assertEquals(itemMOCK, createdItem);

		itemDAO.deleteItem(createdItem);
	}

	@Test(expected = Exception.class)
	public void createItemError() throws Exception {
		Item itemMOCK = new Item("A0000001", "mock name", "mock description", 1234.56, "mock-image.jpg", 1);

		itemDAO.createItem(itemMOCK.getReference(), itemMOCK.getName(), itemMOCK.getDescription(), itemMOCK.getPrice(),
				itemMOCK.getImage(), itemMOCK.getStock());

	}

	@Test
	public void findAllItemsOk() throws Exception {
		List<Item> items = itemDAO.findAllItems();
		Assert.assertNotNull(items);
		Assert.assertEquals("Should get 4 items from the database", 4, items.size());
	}

	@Test
	public void findItemByReferenceOk() throws Exception {
		String existingReference = "A0000001";

		Item item = itemDAO.findItemByReference(existingReference);
		Assert.assertNotNull(item);
		Assert.assertTrue(item.getReference().equals(existingReference));
	}

	@Test
	public void findItemByReferenceNotFound() throws Exception {
		String unknownReference = "UNKNOWN";

		Item item = itemDAO.findItemByReference(unknownReference);
		Assert.assertNull(item);
	}

	@Test
	public void findItemsByPriceBetweenOk() throws Exception {
		double minPrice = 22d;
		double maxPrice = 24d;

		Item itemA0000001 = new Item("A0000001");
		Item itemA0000004 = new Item("A0000004");

		List<Item> items = itemDAO.findItemsByPriceBetween(minPrice, maxPrice);
		Assert.assertNotNull(items);
		Assert.assertEquals("Should get 2 items from the database", 2, items.size());
		Assert.assertTrue(items.contains(itemA0000001));
		Assert.assertTrue(items.contains(itemA0000004));

	}

	@Test
	public void findItemsByStockLessThanOk() throws Exception {
		int stock = 1000;

		Item itemA0000003 = new Item("A0000003");
		Item itemA0000004 = new Item("A0000004");

		List<Item> items = itemDAO.findItemsByStockLessThan(stock);
		Assert.assertNotNull(items);
		Assert.assertEquals("Should get 2 items from the database", 2, items.size());
		Assert.assertTrue(items.contains(itemA0000003));
		Assert.assertTrue(items.contains(itemA0000004));
	}

	@Test
	public void updateItemPriceOk() throws Exception {
		Item itemMOCK = new Item("MOCK-REFERENCE", "mock name", "mock description", 1234.56, "mock-image.jpg", 1);

		Item createdItem = itemDAO.createItem(itemMOCK.getReference(), itemMOCK.getName(), itemMOCK.getDescription(),
				itemMOCK.getPrice(), itemMOCK.getImage(), itemMOCK.getStock());

		Assert.assertNotNull(createdItem);
		Assert.assertEquals(itemMOCK, createdItem);

		double newPrice = 1000d;
		Item updatedItem = itemDAO.updateItemPrice(createdItem, newPrice);

		Assert.assertEquals(newPrice, updatedItem.getPrice(), PRICE_ERROR_TOLERANCE);

		itemDAO.deleteItem(createdItem);
	}

	@Test
	public void updateItemStockOk() throws Exception {
		Item itemMOCK = new Item("MOCK-REFERENCE", "mock name", "mock description", 1234.56, "mock-image.jpg", 1);

		Item createdItem = itemDAO.createItem(itemMOCK.getReference(), itemMOCK.getName(), itemMOCK.getDescription(),
				itemMOCK.getPrice(), itemMOCK.getImage(), itemMOCK.getStock());

		Assert.assertNotNull(createdItem);
		Assert.assertEquals(itemMOCK, createdItem);

		int newStock = 100;
		Item updatedItem = itemDAO.updateItemStock(createdItem, newStock);

		Assert.assertEquals(newStock, updatedItem.getStock());

		itemDAO.deleteItem(createdItem);
	}

	@Test
	public void deleteItem() throws Exception {
		Item itemMOCK = new Item("MOCK-REFERENCE", "mock name", "mock description", 1234.56, "mock-image.jpg", 1);

		Item createdItem = itemDAO.createItem(itemMOCK.getReference(), itemMOCK.getName(), itemMOCK.getDescription(),
				itemMOCK.getPrice(), itemMOCK.getImage(), itemMOCK.getStock());

		Assert.assertNotNull(createdItem);
		Assert.assertEquals(itemMOCK, createdItem);

		itemDAO.deleteItem(createdItem);

		Item deletedItem = itemDAO.findItemByReference(itemMOCK.getReference());
		Assert.assertNull(deletedItem);
	}

}
