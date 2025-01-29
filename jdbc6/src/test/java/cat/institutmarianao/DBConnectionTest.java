package cat.institutmarianao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.institutmarianao.db.connection.DBConnection;

public class DBConnectionTest {
	private DBConnection dBConnection;
	private Connection connection;

	@BeforeEach
	public void setUp() {
		System.out.println("BeforeEach");
		dBConnection = new DBConnection("db.properties");
	}

	@AfterEach
	public void cleanUp() throws SQLException {
		System.out.println("AfterEach");
		if (connection != null) {
			connection.close();
		}
	}

	@Test
	public void getConnectionOk() throws IOException, SQLException, ClassNotFoundException {
		System.out.println("getConnectionOk");
		// Provar el mètode
		connection = dBConnection.getConnection();

		// Asserts
		assertEquals("H2 JDBC Driver", connection.getMetaData().getDriverName(),
				"El driver hauria de ser H2 JDBC Driver");
		assertEquals("SOCIOC_DB", connection.getCatalog(), "El catàleg hauria de ser SOCIOC_DB");
	}

	@Test
	public void getConnectionWrongDriverGetsNull() {
		System.out.println("getConnectionWrongDriverGetsNull");
		assertThrows(ClassNotFoundException.class, () -> {
			DBConnection dBWrongConnection = new DBConnection("db_wrong_driver.properties");
			connection = dBWrongConnection.getConnection();
		}, "S'esperava una excepció ClassNotFoundException");
	}
}
