package cat.institutmarianao.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.institutmarianao.domain.Item;
import cat.institutmarianao.item.mock.Mock;
import cat.institutmarianao.service.ItemService;

@RunWith(Arquillian.class)
public class ItemServiceTest {

	private static final double PRICE_ERROR_TOLERANCE = 0.0001;

	@Inject
	private ItemService itemService;

	@Deployment(testable = true)
	public static JavaArchive createTestableDeployment() {
		final JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "example.jar")
				.addClasses(ItemService.class, ItemServiceImpl.class)
				.addAsManifestResource("META-INF/persistence-test.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
		return jar;
	}

	@Test
	public void getAllItemsOk() {

		Item item0 = Mock.getItem0();
		Item item1 = Mock.getItem1();

		itemService.create(item0);
		itemService.create(item1);

		List<Item> items = itemService.getAllItems();
		Assert.assertEquals(2, items.size());
		Assert.assertTrue(items.contains(item0));
		Assert.assertTrue(items.contains(item1));

		itemService.remove(item0);
		itemService.remove(item1);
	}

	@Test
	public void getByReferenceOk() {
		Item item0 = Mock.getItem0();
		Item item1 = Mock.getItem1();

		itemService.create(item0);
		itemService.create(item1);

		Item item = itemService.getByReference(item0.getReference());
		Assert.assertEquals(item0, item);

		itemService.remove(item0);
		itemService.remove(item1);
	}

	@Test
	public void findItemsByPriceBetweenOk() {
		Item item0 = Mock.getItem0();
		Item item1 = Mock.getItem1();
		Item item2 = Mock.getItem2();
		Item item3 = Mock.getItem3();

		item0.setPrice(100d);
		item1.setPrice(200d);
		item2.setPrice(300d);
		item3.setPrice(400d);

		itemService.create(item0);
		itemService.create(item1);
		itemService.create(item2);
		itemService.create(item3);

		List<Item> items = itemService.findItemsByPriceBetween(item1.getPrice(), item2.getPrice());
		Assert.assertEquals(2, items.size());
		Assert.assertTrue(items.contains(item1));
		Assert.assertTrue(items.contains(item2));

		itemService.remove(item0);
		itemService.remove(item1);
		itemService.remove(item2);
		itemService.remove(item3);
	}

	@Test
	public void findItemsByStockLessThanOk() {
		Item item0 = Mock.getItem0();
		Item item1 = Mock.getItem1();
		Item item2 = Mock.getItem2();
		Item item3 = Mock.getItem3();

		item0.setStock(100);
		item1.setStock(200);
		item2.setStock(300);
		item3.setStock(400);

		itemService.create(item0);
		itemService.create(item1);
		itemService.create(item2);
		itemService.create(item3);

		List<Item> items = itemService.findItemsByStockLessThan(item2.getStock());
		Assert.assertEquals(2, items.size());
		Assert.assertTrue(items.contains(item0));
		Assert.assertTrue(items.contains(item1));

		itemService.remove(item0);
		itemService.remove(item1);
		itemService.remove(item2);
		itemService.remove(item3);
	}

	@Test
	public void editOk() {
		Item item0 = Mock.getItem0();

		String newName = "New name";
		String newDescription = "New description";
		double newPrice = 99d;
		String newImage = "newImage.png";
		int newStock = 99;

		itemService.create(item0);

		Assert.assertNotEquals(item0.getName(), newName);
		Assert.assertNotEquals(item0.getDescription(), newDescription);
		Assert.assertNotEquals(item0.getPrice(), newPrice);
		Assert.assertNotEquals(item0.getImage(), newImage);
		Assert.assertNotEquals(item0.getStock(), newStock);

		item0.setName(newName);
		item0.setDescription(newDescription);
		item0.setPrice(newPrice);
		item0.setImage(newImage);
		item0.setStock(newStock);

		Item updatedItem = itemService.edit(item0);
		Assert.assertEquals(item0, updatedItem);

		Assert.assertEquals(item0.getName(), newName);
		Assert.assertEquals(item0.getDescription(), newDescription);
		Assert.assertEquals(item0.getPrice(), newPrice, PRICE_ERROR_TOLERANCE);
		Assert.assertEquals(item0.getImage(), newImage);
		Assert.assertEquals(item0.getStock(), newStock);

		itemService.remove(item0);
	}
}
