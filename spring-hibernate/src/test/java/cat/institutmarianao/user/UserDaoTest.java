package cat.institutmarianao.user;

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

import cat.institutmarianao.DaoTestConfig;
import cat.institutmarianao.utils.Mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DaoTestConfig.class })
public class UserDaoTest {
	@Autowired
	private UserDao userDao;

	@Test
	@Transactional
	public void saveOk() {
		/* Setup */
		Timestamp currentTime = new Timestamp(new Date().getTime());

		String username = "john";
		User user = Mock.getUser(null, username);
		user.setCreatedOn(currentTime);

		/* Previous verification - user has no id */
		assertNull(user.getUserId());
		User userFromDb = userDao.findUserByUsername(username);
		assertNull(userFromDb);

		/* Test method */
		userDao.save(user);

		/* Verification */
		userFromDb = userDao.findUserByUsername(username);
		assertNotNull(user.getUserId());
		assertEquals(user.getUserId(), userFromDb.getUserId());
		assertEquals(currentTime, userFromDb.getCreatedOn());

	}
}