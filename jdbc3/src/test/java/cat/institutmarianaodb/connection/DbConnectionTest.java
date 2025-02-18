package cat.institutmarianaodb.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import cat.institutmarianao.db.connection.DbConnection;

class DbConnectionTest {

	private DbConnection dbConnection;
	private Connection connection;

	@AfterEach
	void cleanUp() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	@Test
	void testConnectionOk() {
		dbConnection = new DbConnection("db.properties");
		try {
			connection = dbConnection.getConnection();
			assertNotNull(connection);
			assertEquals("h2", connection.getMetaData().getDatabaseProductName().toLowerCase());
			assertEquals("social", connection.getCatalog().toLowerCase());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConnectionPropertiesNotFound() {
		dbConnection = new DbConnection("db_properties_not_found");
		try {
			connection = dbConnection.getConnection();
			assertNull(connection);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testConnectionBadProperties() {
		dbConnection = new DbConnection("db_bad.properties");
		try {
			connection = dbConnection.getConnection();
			assertNull(connection);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
