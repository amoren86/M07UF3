package cat.institutmarianao.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cat.institutmarianao.config.HibernateH2DatabaseConfig;
import cat.institutmarianao.utils.Mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateH2DatabaseConfig.class })
public class UserDAOTest {
	@Autowired
	private UserDAO userDAO;

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
		User userFromDb = userDAO.findUserByUsername(username);
		assertNull(userFromDb);

		/* Test method */
		userDAO.save(user);

		/* Verification */
		userFromDb = userDAO.findUserByUsername(username);
		assertNotNull(user.getUserId());
		assertEquals(user.getUserId(), userFromDb.getUserId());
		assertEquals(currentTime, userFromDb.getCreatedOn());

	}
}