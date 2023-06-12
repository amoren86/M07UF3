package cat.institutmarianao.item.mock;

import cat.institutmarianao.domain.Item;

public class Mock {
	public static Item getItem0() {
		Item item = new Item("A0000001", "Big Pig", "Cerdito bonito y apestoso de gran calidad", 23.90, "cerdo.jpg",
				5350);
		return item;
	}

	public static Item getItem1() {
		Item item = new Item("A0000002", "Silly Monkey", "Simpático monito que hará las delícias de los más pequeños",
				24.90, "mono.jpg", 2491);
		return item;
	}

	public static Item getItem2() {
		Item item = new Item("A0000003", "Fat Bear", "El oso amoroso que le hará coger el sueño con sólo abrazarlo",
				25.00, "oso.jpg", 88);
		return item;
	}

	public static Item getItem3() {
		Item item = new Item("A0000004", "Vaca Paca",
				"La vaca más realista del mundo de los peluches. Sólo le falta andar", 23.80, "vaca.jpg", 150);
		return item;
	}
}
