package cat.institutmarianao.jpa;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cat.institutmarianao.jpa.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

class UserTest {
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	@Before
	void setUp() {
		entityManager = Persistence.createEntityManagerFactory("InMemoryH2PersistenceUnit").createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	@After
	void cleanUp() {
		if (entityManager != null && entityManager.isOpen()) {
			entityManager.close();
		}
	}

	@Test
	void findAllUsers() {
		User user = new User();
		user.setUsername("lovelace");
		user.setName("Ada Lovelace");
		user.setEmail("ada@lovelace.was");
		user.setPassword("P4$$w0rd");
		user.setRank(100);
		user.setActive(true);
		user.setCreatedOn(new Timestamp(new Date().getTime()));

		entityTransaction.begin();
		entityManager.persist(user);
		entityTransaction.commit();

		Query query = entityManager.createNativeQuery(
				"SELECT id, username, name, email, password, rank, active, created_on FROM users", User.class);

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();

		assertEquals(1, userList.size());
		assertEquals("lovelace", userList.get(0).getUsername());
	}
}
