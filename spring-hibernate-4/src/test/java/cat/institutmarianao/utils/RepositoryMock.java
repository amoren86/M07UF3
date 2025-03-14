package cat.institutmarianao.utils;

import cat.institutmarianao.spring.model.User;

public class RepositoryMock {

	public static User createUser(Long userId, String username) {
		User user = new User();
		user.setId(userId);
		user.setUsername(username);
		user.setActive(true);
		return user;
	}
}
