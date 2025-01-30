package cat.institutmarianao.db.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
