package cat.institutmarianao.db.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public User createUser(String username, String name, String email, String password) throws Exception {
		String qry = "INSERT INTO users (username, name, email, password) VALUES (?, ?, ?, ?)";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, name);
		preparedStatement.setString(3, email);
		preparedStatement.setString(4, password);
		return createOrUpdateUser(username, preparedStatement);
	}

	public List<User> findAllUsers() throws Exception {
		String qry = "SELECT id, username, name, email, rank, active, created_on FROM users";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		return executeQuery(preparedStatement);
	}

	public List<User> findActiveUsers() throws Exception {
		String qry = "SELECT id, username, name, email, rank, active, created_on FROM users WHERE active=true";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		return executeQuery(preparedStatement);
	}

	public User findUserByEmail(String userEmail) throws Exception {
		String qry = "SELECT * FROM users WHERE email = ?";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, userEmail);
		return findUniqueResult(preparedStatement);
	}

	public User findUserByUsername(String username) throws Exception {
		String qry = "SELECT * FROM users WHERE username =?";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, username);
		return findUniqueResult(preparedStatement);
	}

	public User createUser(String username, String name, String email) throws Exception {
		String qry = "INSERT INTO users (username, name, email) VALUES (?, ?, ?)";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, name);
		preparedStatement.setString(3, email);
		return createOrUpdateUser(username, preparedStatement);
	}

	public User updateUserEmail(User user, String newEmail) throws Exception {
		String qry = "UPDATE users SET email = ?  WHERE id = ? ";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setString(1, newEmail);
		preparedStatement.setInt(2, user.getId());
		return createOrUpdateUser(user.getUsername(), preparedStatement);
	}

	public void deleteUser(User user) throws Exception {
		String qry = "DELETE FROM users WHERE id = ?";
		PreparedStatement preparedStatement = getPreparedStatement(qry);
		preparedStatement.setInt(1, user.getId());
		createOrUpdateUser(user.getUsername(), preparedStatement);
	}

	private User createOrUpdateUser(String username, PreparedStatement preparedStatement) throws Exception {
		int result = executeUpdateQuery(preparedStatement);
		if (result == 0) {
			throw new Exception("Error creating user");
		}
		return findUserByUsername(username);
	}

	private User findUniqueResult(PreparedStatement preparedStatement) throws Exception {
		List<User> users = executeQuery(preparedStatement);
		if (users.isEmpty()) {
			return null;
		}
		if (users.size() > 1) {
			throw new Exception("Only one result expected");
		}
		return users.get(0);
	}

	private List<User> executeQuery(PreparedStatement preparedStatement) {
		List<User> users = new ArrayList<>();

		try (ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				User user = buildUserFromResultSet(rs);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	private PreparedStatement getPreparedStatement(String query) throws Exception {
		if (getConnection() == null) {
			try {
				setConnection(dBConnection.getConnection());
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return getConnection().prepareStatement(query);
	}

	private int executeUpdateQuery(PreparedStatement preparedStatement) throws Exception {
		int result = 0;
		if (getConnection() == null) {
			try {
				setConnection(dBConnection.getConnection());
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		try {
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private User buildUserFromResultSet(ResultSet rs) throws SQLException {
		int userId = rs.getInt("id");
		String username = rs.getString("username");
		String name = rs.getString("name");
		String email = rs.getString("email");
		int rank = rs.getInt("rank");
		boolean active = rs.getBoolean("active");
		Timestamp timestamp = rs.getTimestamp("created_on");
		return new User(userId, username, name, email, rank, timestamp, active);
	}

}
