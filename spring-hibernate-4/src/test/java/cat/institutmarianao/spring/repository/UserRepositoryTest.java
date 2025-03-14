package cat.institutmarianao.spring.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import cat.institutmarianao.spring.H2DataSourceConfig;
import cat.institutmarianao.spring.HibernateConfig;
import cat.institutmarianao.spring.RepositoryConfig;
import cat.institutmarianao.spring.model.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RepositoryConfig.class, HibernateConfig.class, H2DataSourceConfig.class })
class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	void createOk() {
		/* Setup */
		Timestamp currentTime = new Timestamp(new Date().getTime());

		String username = "john";
		User user = createUser(null, username);
		user.setCreatedOn(currentTime);

		/* Previous verification - user has no id */
		assertNull(user.getId());
		User userFromDb = userRepository.findByUsername(username);
		assertNull(userFromDb);

		/* Test method */
		userRepository.create(user);

		/* Verification */
		userFromDb = userRepository.findByUsername(username);
		assertNotNull(user.getId());
		assertEquals(user.getId(), userFromDb.getId());
		assertEquals(currentTime, userFromDb.getCreatedOn());

	}

	private static User createUser(Long userId, String username) {
		User user = new User();
		user.setId(userId);
		user.setUsername(username);
		user.setActive(true);
		return user;
	}
}