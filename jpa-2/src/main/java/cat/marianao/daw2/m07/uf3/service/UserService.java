package cat.marianao.daw2.m07.uf3.service;

import java.util.List;

import javax.ejb.Local;

import cat.marianao.daw2.m07.uf3.domain.User;

@Local
public interface UserService {
	List<User> getAllUsers();

	void create(User user);

	void edit(User user);

	void remove(User user);
}
