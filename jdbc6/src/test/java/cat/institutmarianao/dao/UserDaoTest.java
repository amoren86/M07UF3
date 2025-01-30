package cat.institutmarianao.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.institutmarianao.db.connection.DbConnection;
import cat.institutmarianao.db.dao.UserDao;
import cat.institutmarianao.model.User;

public class UserDaoTest {
	private DbConnection dBConnection;
	private String connectionProperties = "db_test.properties";
	private UserDao userDAO;

	@BeforeEach
	public void setUp() {
		dBConnection = new DbConnection(connectionProperties);
		userDAO = new UserDao(dBConnection);
	}

	@AfterEach
	public void tearDown() throws IOException, SQLException {
		userDAO.getConnection().close();
	}

	@Test
	public void findAllUsers() throws Exception {
		List<User> users = userDAO.findAllUsers();
		assertEquals(2, users.size(), "Hauriem de tenir 2 usuaris a la base de dades");
	}

	@Test
	public void findUserByEmail() throws Exception {
		String existingEmail = "john@email.com";
		String unknownEmail = "does.not@exist.com";

		User user = userDAO.findUserByEmail(existingEmail);
		assertNotNull(user, "L'usuari hauria d'existir");
		user = userDAO.findUserByEmail(unknownEmail);
		assertNull(user, "L'usuari no hauria d'existir");
	}

	@Test
	public void findUserByUsername() throws Exception {
		String existingUsername = "user1";
		String unknownUsername = "unknown";

		User user = userDAO.findUserByUsername(existingUsername);
		assertNotNull(user, "L'usuari hauria d'existir");
		user = userDAO.findUserByUsername(unknownUsername);
		assertNull(user, "L'usuari no hauria d'existir");
	}

	@Test
	public void createUser() throws Exception {
		String username = "testUser";
		String name = "Pete Test";
		String email = "pete@email.com";
		User createdUser = userDAO.createUser(username, name, email);

		assertNotNull(createdUser, "L'usuari hauria de ser creat");
		assertEquals(username, createdUser.getUsername(), "El nom d'usuari no coincideix");
		assertNotEquals(0, createdUser.getId(), "L'ID de l'usuari no hauria de ser 0");
	}

	@Test
	public void updateUserEmail() throws Exception {
		String username = "testUser";
		String name = "Pete Test";
		String email = "pete@email.com";
		User createdUser = userDAO.createUser(username, name, email);

		assertNotNull(createdUser, "L'usuari hauria de ser creat");
		assertEquals(email, createdUser.getEmail(), "El correu electrònic inicial hauria de coincidir");

		User updatedUser = userDAO.updateUserEmail(createdUser, "new@email.com");

		assertEquals(createdUser.getId(), updatedUser.getId(), "L'ID ha de ser el mateix després de l'actualització");
		assertEquals("new@email.com", updatedUser.getEmail(), "El correu electrònic hauria d'haver estat actualitzat");
	}

	@Test
	public void deleteUser() throws Exception {
		String username = "testUser";
		String name = "Pete Test";
		String email = "pete@email.com";
		User createdUser = userDAO.createUser(username, name, email);

		assertNotNull(createdUser, "L'usuari hauria de ser creat");

		userDAO.deleteUser(createdUser);
		User deletedUser = userDAO.findUserByUsername(username);

		assertNull(deletedUser, "L'usuari hauria d'haver estat eliminat");
	}

	@Test
	public void createUserWithError() {
		String username = "sl','sls";
		String name = "Pete Test";
		String email = "pete@email.com";

		assertThrows(Exception.class, () -> {
			userDAO.createUser(username, name, email);
		}, "S'esperava una excepció per SQL injection");
	}
}
