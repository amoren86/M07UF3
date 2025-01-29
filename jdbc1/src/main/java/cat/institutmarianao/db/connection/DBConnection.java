package cat.institutmarianao.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public Connection getConnection() throws SQLException {
		Connection con = null;
		return DriverManager.getConnection("jdbc:h2:mem:social", "user", "password");
	}
}
