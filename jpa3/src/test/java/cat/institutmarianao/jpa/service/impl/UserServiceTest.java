package cat.institutmarianao.jpa.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cat.institutmarianao.jpa.model.User;
import cat.institutmarianao.jpa.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class UserServiceTest {
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private UserService userService;

	@Before
	public void setUp() {
		entityManager = Persistence.createEntityManagerFactory("InMemoryH2PersistenceUnit").createEntityManager();
		userService = new UserServiceImpl(entityManager);
		entityTransaction = entityManager.getTransaction();
	}

	@After
	public void cleanUp() {
		entityManager.close();
	}

	@Test
	public void testCreateUser() {
		User user = createTestUser("lovelace", "Ada Lovelace", "ada@lovelace.was");
		assertNull(user.getId());

		entityTransaction.begin();
		userService.create(user);
		entityTransaction.commit();
		assertNotNull(user.getId());

		User foundUser = userService.findUserByUsername("lovelace");
		assertNotNull(foundUser);
		assertEquals(user, foundUser);
	}

	@Test
	public void testEditUser() {
		User user = createTestUser("babbage", "Charles Babbage", "charles@babbage.was");
		assertNull(user.getId());

		entityTransaction.begin();
		userService.create(user);
		entityTransaction.commit();
		assertNotNull(user.getId());
		assertNotEquals("new.charles@babbage.was", user.getEmail());

		entityTransaction.begin();
		user.setEmail("new.charles@babbage.was");
		userService.edit(user);
		entityTransaction.commit();

		User updatedUser = userService.findUserByUsername("babbage");
		assertEquals("new.charles@babbage.was", updatedUser.getEmail());
	}

	@Test
	public void testRemoveUser() {
		User user = createTestUser("byron", "Lord Byron", "lord@byron.was");
		assertNull(user.getId());

		entityTransaction.begin();
		userService.create(user);
		entityTransaction.commit();
		assertNotNull(user.getId());

		entityTransaction.begin();
		userService.remove(user);
		entityTransaction.commit();

		assertThrows(NoResultException.class, () -> userService.findUserByUsername("byron"));
	}

	private User createTestUser(String username, String name, String email) {
		User user = new User();
		user.setUsername(username);
		user.setName(name);
		user.setEmail(email);
		user.setActive(true);
		user.setCreatedOn(new Timestamp(new Date().getTime()));
		user.setPassword("password");
		user.setRank(100);
		return user;
	}

}