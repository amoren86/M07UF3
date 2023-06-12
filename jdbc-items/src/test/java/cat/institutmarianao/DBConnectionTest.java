package cat.institutmarianao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cat.institutmarianao.DBConnection;

public class DBConnectionTest {
	DBConnection dBConnection;
	Connection connection;

	@Before
	public void setUp() {
		// System.out.println("Before");
		dBConnection = new DBConnection("db.properties");
	}

	@After
	public void cleanUp() throws SQLException {
		// System.out.println("After");
		if (connection != null) {
			connection.close();
		}
	}

	@Test
	public void getConnectionOk() throws IOException, SQLException, ClassNotFoundException {
		// System.out.println("getConnectionOk");
		// Test this method
		connection = dBConnection.getConnection();

		// Assert results
		Assert.assertEquals("H2 JDBC Driver", connection.getMetaData().getDriverName());
		Assert.assertEquals("SOCIOC_DB", connection.getCatalog());
	}

	@Test(expected = ClassNotFoundException.class)
	public void getConnectionWrongDriverGetsNull() throws SQLException, IOException, ClassNotFoundException {
		DBConnection dBWrongConnection = new DBConnection("db_wrong_driver.properties");
		connection = dBWrongConnection.getConnection();
	}
}
