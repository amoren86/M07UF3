package cat.institutmarianao.db.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.institutmarianao.db.connection.DbConnection;
import cat.institutmarianao.model.User;

class UserDaoTest {
	private UserDao userDao;

	@BeforeEach
	void setUp() {
		DbConnection dBConnection = new DbConnection("db_dao_test.properties");
		userDao = new UserDao(dBConnection);
	}

	@AfterEach
	void tearDown() throws SQLException {
		if (userDao.getConnection() != null) {
			userDao.getConnection().close();
		}
	}

	@Test
	void testFindAllUsers() throws IOException, SQLException {
		List<User> users = userDao.findAllUsers();
		assertEquals(3, users.size());
	}

	@Test
	void findUserByUsernameOk() throws IOException, SQLException {
		String username = "lovelace";

		User user = userDao.findUserByUsername(username);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertEquals("Ada Lovelace", user.getName());
		assertEquals("ada@lovelace.was", user.getEmail());
	}

	@Test
	void findUserByUsernameNotFound() throws IOException, SQLException {
		String username = "user_not_found";

		User user = userDao.findUserByUsername(username);
		assertNull(user);
	}

	@Test
	void findUserByEmailOk() throws IOException, SQLException {
		String email = "ada@lovelace.was";

		User user = userDao.findUserByEmail(email);
		assertNotNull(user);
		assertEquals("lovelace", user.getUsername());
		assertEquals("Ada Lovelace", user.getName());
		assertEquals(email, user.getEmail());
	}

	@Test
	void findUserByEmailNotFound() throws IOException, SQLException {
		String email = "email@not.found";

		User user = userDao.findUserByEmail(email);
		assertNull(user);
	}

	@Test
	void createUserOk() throws IOException, SQLException {
		String username = "somerville";
		String name = "Mary Somerville";
		String email = "mary@somerville.was";

		User createdUser = userDao.createUser(username, name, email);

		assertNotNull(createdUser);
		assertNotEquals(0, createdUser.getId());
		assertEquals(username, createdUser.getUsername());
		assertEquals(name, createdUser.getName());
		assertEquals(email, createdUser.getEmail());
	}

	@Test
	void createUserWithInjectionError() throws IOException, SQLException {
		// The following line produces that won't pass the test due it counts as two
		// fields
		String username = "a','b";
		String name = "Exception";
		String email = "exception@exception.was";

		User createdUser = userDao.createUser(username, name, email);

		assertNotNull(createdUser);
		assertNotEquals(0, createdUser.getId());
		assertEquals(username, createdUser.getUsername());
		assertEquals(name, createdUser.getName());
		assertEquals(email, createdUser.getEmail());
	}

	@Test
	void updateUserEmailOk() throws IOException, SQLException {
		String username = "lovelace";
		User user = userDao.findUserByUsername(username);
		assertNotNull(user);

		String newEmail = "ada@lovelace.new";
		assertNotEquals(newEmail, user.getEmail());

		User updatedUser = userDao.updateEmail(user, newEmail);

		assertNotNull(updatedUser);
		assertEquals(user.getId(), updatedUser.getId());
		assertEquals(username, updatedUser.getUsername());
		assertEquals(newEmail, updatedUser.getEmail());
	}

	@Test
	void deleteUserOk() throws IOException, SQLException {
		String username = "lovelace";
		User user = userDao.findUserByUsername(username);
		assertNotNull(user);

		userDao.deleteUser(user);

		User deletedUser = userDao.findUserByUsername(username);
		assertNull(deletedUser);
	}

}
