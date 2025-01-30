package cat.institutmarianao.db.connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
	private String dbProperties;

	public DbConnection(String dbProperties) {
		this.dbProperties = dbProperties;
	}

	public Connection getConnection() throws IOException, SQLException {
		Properties props = new Properties();
		ClassLoader classLoader = getClass().getClassLoader();
		URL urlResource = classLoader.getResource(dbProperties);

		if (urlResource != null) {
			try (InputStream resourceAsStream = urlResource.openStream()) {
				props.load(resourceAsStream);
				return DriverManager.getConnection(props.getProperty("db_url"), props.getProperty("db_username"),
						props.getProperty("db_password"));
			}
		}

		return null;
	}
}

