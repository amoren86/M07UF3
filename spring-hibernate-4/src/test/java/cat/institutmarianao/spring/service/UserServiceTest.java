package cat.institutmarianao.spring.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cat.institutmarianao.spring.MockRepositoryConfig;
import cat.institutmarianao.spring.ServicesConfig;
import cat.institutmarianao.spring.model.User;
import cat.institutmarianao.spring.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ServicesConfig.class, MockRepositoryConfig.class })
class UserServiceTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Test
	void findByUsernameOk() {
		/* Setup */
		String username = "test";
		User user = mock(User.class);

		when(userRepository.findByUsername(username)).thenReturn(user);

		/* Test */
		User userFromDb = userService.findByUsername(username);

		/* Verification */
		assertSame(user, userFromDb);
		verify(userRepository, times(1)).findByUsername(username);
	}
}