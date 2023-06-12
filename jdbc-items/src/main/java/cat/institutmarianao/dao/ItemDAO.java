package cat.institutmarianao.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cat.institutmarianao.DBConnection;

public class ItemDAO {
	private DBConnection dBConnection;
	private Connection connection;

	public ItemDAO(DBConnection dBConnection) {
		this.dBConnection = dBConnection;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Item createItem(String reference, String name, String description, Double price, String image, int stock)
			throws Exception {
		String qry = "INSERT INTO items (reference, name, description, price, image, stock) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, reference);
		preparedStatement.setString(2, name);
		preparedStatement.setString(3, description);
		preparedStatement.setDouble(4, price);
		preparedStatement.setString(5, image);
		preparedStatement.setInt(6, stock);
		return createOrUpdateItem(reference, preparedStatement);
	}

	public List<Item> findAllItems() throws Exception {
		String qry = "SELECT reference, name, description, price, image, stock FROM items";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		List<Item> items = executeQuery(preparedStatement);
		return items;
	}

	public Item findItemByReference(String reference) throws Exception {
		String qry = "SELECT reference, name, description, price, image, stock FROM items WHERE reference =?";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, reference);
		return findUniqueResult(preparedStatement);
	}

	public List<Item> findItemsByPriceBetween(Double minPrice, Double maxPrice) throws Exception {
		String qry = "SELECT reference, name, description, price, image, stock FROM items WHERE price BETWEEN ? and ?";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setDouble(1, minPrice);
		preparedStatement.setDouble(2, maxPrice);
		List<Item> items = executeQuery(preparedStatement);
		return items;
	}

	public List<Item> findItemsByStockLessThan(int stock) throws Exception {
		String qry = "SELECT reference, name, description, price, image, stock FROM items WHERE stock < ?";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setInt(1, stock);
		List<Item> items = executeQuery(preparedStatement);
		return items;
	}

	public Item updateItemPrice(Item item, Double newPrice) throws Exception {
		String qry = "UPDATE items SET price = ?  WHERE reference = ? ";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setDouble(1, newPrice);
		preparedStatement.setString(2, item.getReference());
		return createOrUpdateItem(item.getReference(), preparedStatement);
	}

	public Item updateItemStock(Item item, int newStock) throws Exception {
		String qry = "UPDATE items SET stock = ?  WHERE reference = ? ";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setInt(1, newStock);
		preparedStatement.setString(2, item.getReference());
		return createOrUpdateItem(item.getReference(), preparedStatement);
	}

	public void deleteItem(Item item) throws Exception {
		String qry = "DELETE FROM items WHERE reference = ?";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, item.getReference());
		createOrUpdateItem(item.getReference(), preparedStatement);
	}

	private Item createOrUpdateItem(String reference, PreparedStatement preparedStatement) throws Exception {
		int result = executeUpdateQuery(preparedStatement);
		if (result == 0) {
			throw new Exception("Error creating item");
		}
		return findItemByReference(reference);
	}

	private Item findUniqueResult(PreparedStatement preparedStatement) throws Exception {
		List<Item> items = executeQuery(preparedStatement);
		if (items.isEmpty()) {
			return null;
		}
		if (items.size() > 1) {
			throw new Exception("Only one result expected");
		}
		return items.get(0);
	}

	private List<Item> executeQuery(PreparedStatement preparedStatement) {
		List<Item> items = new ArrayList<>();

		try (ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				Item item = buildItemFromResultSet(rs);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

	private PreparedStatement getPreparedStatement(String query) throws Exception {
		if (getConnection() == null) {
			try {
				setConnection(dBConnection.getConnection());
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return getConnection().prepareStatement(query);
	}

	private int executeUpdateQuery(PreparedStatement preparedStatement) throws Exception {
		int result = 0;
		if (getConnection() == null) {
			try {
				setConnection(dBConnection.getConnection());
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		try {
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	private Item buildItemFromResultSet(ResultSet rs) throws SQLException {
		String reference = rs.getString("reference");
		String name = rs.getString("name");
		String description = rs.getString("description");
		Double price = rs.getDouble("price");
		String image = rs.getString("image");
		Integer stock = rs.getInt("stock");
		Item item = new Item(reference, name, description, price, image, stock);
		return item;
	}

}
