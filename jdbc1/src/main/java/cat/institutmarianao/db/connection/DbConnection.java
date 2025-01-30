package cat.institutmarianao.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:h2:mem:social", "user", "password");
	}
}
