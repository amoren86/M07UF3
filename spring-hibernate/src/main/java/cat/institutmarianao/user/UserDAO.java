package cat.institutmarianao.user;

import java.util.List;

public interface UserDAO {
	User getById(Integer id);

	void save(User user);

	User edit(User user);

	void remove(User user);

	User findUserByUsername(String username);

	User findUserWithHighestRank();

	List<User> findActiveUsers();
}
