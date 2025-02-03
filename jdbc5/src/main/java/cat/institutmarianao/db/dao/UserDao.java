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
		return executeQuery(qry);
	}

	public User findUserByUsername(String username) throws IOException, SQLException {
		String qry = String.format(
				"SELECT id, username, name, email, rank, active, created_on FROM users WHERE username = '%s'",
				username);
		return findUniqueResult(qry);
	}

	public User findUserByEmail(String email) throws IOException, SQLException {
		String qry = String.format(
				"SELECT id, username, name, email, rank, active, created_on FROM users WHERE email = '%s'", email);
		return findUniqueResult(qry);
	}

	public User createUser(String username, String name, String email) throws IOException, SQLException {
		String qry = String.format("INSERT INTO users (username, name, email) VALUES ('%s', '%s', '%s')", username,
				name, email);
		return createUser(username, qry);
	}

	public User updateEmail(User user, String newEmail) throws IOException, SQLException {
		String qry = String.format("UPDATE users SET email = '%s' WHERE id = '%s'", newEmail, user.getId());
		return updateUser(user.getUsername(), qry);
	}

	public void deleteUser(User user) throws IOException, SQLException {
		String qry = String.format("DELETE FROM users WHERE id = '%s'", user.getId());
		updateUser(user.getUsername(), qry);
	}

	private List<User> executeQuery(String query) throws IOException, SQLException {
		if (connection == null) {
			connection = dBConnection.getConnection();
		}
		List<User> users = new ArrayList<>();

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				User user = buildUserFromResultSet(rs);
				users.add(user);
			}
		}
		return users;
	}

	private User findUniqueResult(String query) throws IOException, SQLException {
		List<User> users = executeQuery(query);
		if (users.isEmpty()) {
			return null;
		}
		if (users.size() > 1) {
			throw new SQLException("Only one result expected");
		}
		return users.get(0);
	}

	private int executeUpdateQuery(String query) throws IOException, SQLException {
		if (connection == null) {
			connection = dBConnection.getConnection();
		}
		try (Statement stmt = connection.createStatement()) {
			return stmt.executeUpdate(query);
		}
	}

	private User updateUser(String username, String query) throws IOException, SQLException {
		executeUpdateQuery(query);
		return findUserByUsername(username);
	}

	private User createUser(String username, String query) throws IOException, SQLException {
		int result = executeUpdateQuery(query);
		if (result == 0) {
			throw new SQLException("Error creating user");
		}
		return findUserByUsername(username);
	}

	private User buildUserFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("username"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setRank(rs.getInt("rank"));
		user.setActive(rs.getBoolean("active"));
		user.setCreatedOn(rs.getTimestamp("created_on"));
		return user;
	}

}