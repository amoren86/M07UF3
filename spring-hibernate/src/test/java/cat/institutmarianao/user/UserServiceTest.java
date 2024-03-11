package cat.institutmarianao.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cat.institutmarianao.ServicesTestConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ServicesTestConfig.class })
public class UserServiceTest {
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void getUserOk() {
		/* Setup */
		String username = "test";
		User user = new User();
		user.setUsername(username);
		user.setUserId(1);

		when(userDao.findUserByUsername(username)).thenReturn(user);

		/* Test */
		User userFromDb = userService.findUserByUsername(username);

		/* Verification */
		assertEquals(username, userFromDb.getUsername());
		assertEquals(1, userFromDb.getUserId());
		verify(userDao, times(1)).findUserByUsername(username);
	}
}