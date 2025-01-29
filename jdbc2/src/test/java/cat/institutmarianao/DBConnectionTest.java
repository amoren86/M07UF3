package cat.institutmarianao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.institutmarianao.db.connection.DBConnection;

class DBConnectionTest {
	DBConnection dBConnection;
	Connection connection;

	@BeforeEach
	void setUp() {
		dBConnection = new DBConnection();
	}

	@AfterEach
	void cleanUp() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	@Test
	void connectarAmbLaBaseDeDades() throws IOException, SQLException {
		connection = dBConnection.getConnection();
		assertNotNull(connection);
		assertEquals("H2 JDBC Driver", connection.getMetaData().getDriverName());
		assertEquals("SOCIOC_DB", connection.getCatalog());
	}
}
