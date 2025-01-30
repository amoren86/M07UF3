package cat.institutmarianao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.institutmarianao.db.connection.DbConnection;

class DbConnectionTest {

	private DbConnection dbConnection;
	private Connection connection;

	@BeforeEach
	void setUp() {
		dbConnection = new DbConnection();
	}

	@AfterEach
	void cleanUp() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	@Test
	void testConnectionOk() {
		try {
			connection = dbConnection.getConnection();
			assertNotNull(connection);
			assertEquals("H2", connection.getMetaData().getDatabaseProductName());
			assertEquals("socioc_db", connection.getCatalog().toLowerCase());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
