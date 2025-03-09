package cat.institutmarianao.spring.repository;

import java.util.List;

import cat.institutmarianao.spring.model.User;

public interface UserRepository {
	void create(User user);

	User edit(User user);

	void remove(User user);

	User findByUsername(String username);

	List<User> findActive();

	User findByHighestRank();
}
