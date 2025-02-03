package cat.institutmarianao.db.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cat.institutmarianao.db.connection.DbConnection;
import cat.institutmarianao.model.User;

public class UserDao {
	private DbConnection dBConnection;
	private Connection connection;

	public UserDao(DbConnection dBConnection) {
		this.dBConnection = dBConnection;
	}

	public Connection getConnection() {
		return connection;
	}

	public List<User> findAllUsers() throws IOException, SQLException {
		String qry = "SELECT id, username, name, email, rank, active, created_on FROM users";
		List<User> users = new ArrayList<>();

		User user = null;
		try (Connection conn = dBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(qry);) {
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setRank(rs.getInt("rank"));
				user.setActive(rs.getBoolean("active"));
				user.setCreatedOn(rs.getTimestamp("created_on"));

				users.add(user);
			}
			return users;
		}
	}

	public User findUserByUsername(String username) throws IOException, SQLException {
		String qry = String.format(
				"SELECT id, username, name, email, rank, active, created_on FROM users WHERE username = '%s'",
				username);

		User user = null;
		try (Connection conn = dBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(qry)) {
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setRank(rs.getInt("rank"));
				user.setActive(rs.getBoolean("active"));
				user.setCreatedOn(rs.getTimestamp("created_on"));
			}
			return user;
		}
	}

	public User findUserByEmail(String email) throws IOException, SQLException {
		String qry = String.format(
				"SELECT id, username, name, email, rank, active, created_on FROM users WHERE email = '%s'", email);

		User user = null;
		try (Connection conn = dBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(qry)) {
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setRank(rs.getInt("rank"));
				user.setActive(rs.getBoolean("active"));
				user.setCreatedOn(rs.getTimestamp("created_on"));
			}
			return user;
		}
	}

}
