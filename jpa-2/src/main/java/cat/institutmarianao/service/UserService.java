package cat.institutmarianao.service;

import java.util.List;

import javax.ejb.Local;

import cat.institutmarianao.domain.User;

@Local
public interface UserService {
	List<User> getAllUsers();

	void create(User user);

	void edit(User user);

	void remove(User user);
}
