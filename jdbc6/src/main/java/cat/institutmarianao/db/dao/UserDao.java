package cat.institutmarianao.db.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	private PreparedStatement getPreparedStatement(String query) throws IOException, SQLException {
		if (connection == null) {
			connection = dBConnection.getConnection();
		}
		return connection.prepareStatement(query);
	}

	public List<User> findAllUsers() throws IOException, SQLException {
		String qry = "SELECT id, username, name, email, rank, active, created_on FROM users";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		return executeQuery(preparedStatement);
	}

	public List<User> findActiveUsers() throws IOException, SQLException {
		String qry = "SELECT id, username, name, email, rank, active, created_on FROM users WHERE active=true";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		return executeQuery(preparedStatement);
	}

	public User findUserByUsername(String username) throws IOException, SQLException {
		String qry = "SELECT id, username, name, email, rank, active, created_on FROM users WHERE username =?";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, username);
		return findUniqueResult(preparedStatement);
	}

	public User findUserByEmail(String userEmail) throws IOException, SQLException {
		String qry = "SELECT id, username, name, email, rank, active, created_on FROM users WHERE email = ?";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, userEmail);
		return findUniqueResult(preparedStatement);
	}

	public User createUser(String username, String name, String email) throws IOException, SQLException {
		String qry = "INSERT INTO users (username, name, email) VALUES (?, ?, ?)";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, name);
		preparedStatement.setString(3, email);
		return createUser(username, preparedStatement);
	}

	public User updateEmail(User user, String newEmail) throws IOException, SQLException {
		String qry = "UPDATE users SET email = ?  WHERE id = ? ";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, newEmail);
		preparedStatement.setInt(2, user.getId());
		return updateUser(user.getUsername(), preparedStatement);
	}

	public void deleteUser(User user) throws IOException, SQLException {
		String qry = "DELETE FROM users WHERE id = ?";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setInt(1, user.getId());
		updateUser(user.getUsername(), preparedStatement);
	}

	private List<User> executeQuery(PreparedStatement preparedStatement) throws IOException, SQLException {
		if (connection == null) {
			connection = dBConnection.getConnection();
		}
		List<User> users = new ArrayList<>();

		try (ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				User user = buildUserFromResultSet(rs);
				users.add(user);
			}
		}
		return users;
	}

	private User findUniqueResult(PreparedStatement preparedStatement) throws IOException, SQLException {
		List<User> users = executeQuery(preparedStatement);
		if (users.isEmpty()) {
			return null;
		}
		if (users.size() > 1) {
			throw new SQLException("Only one result expected");
		}
		return users.get(0);
	}

	private int executeUpdateQuery(PreparedStatement preparedStatement) throws IOException, SQLException {
		if (connection == null) {
			connection = dBConnection.getConnection();
		}
		return preparedStatement.executeUpdate();
	}

	private User createUser(String username, PreparedStatement preparedStatement) throws IOException, SQLException {
		int result = executeUpdateQuery(preparedStatement);
		if (result == 0) {
			throw new SQLException("Error creating user");
		}
		return findUserByUsername(username);
	}

	private User updateUser(String username, PreparedStatement preparedStatement) throws IOException, SQLException {
		executeUpdateQuery(preparedStatement);
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
