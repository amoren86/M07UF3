package cat.institutmarianao.jpa.service;

import cat.institutmarianao.jpa.model.User;

public interface UserService {
	void create(User user);

	User edit(User user);

	void remove(User user);

	User findUserByUsername(String username);
}
