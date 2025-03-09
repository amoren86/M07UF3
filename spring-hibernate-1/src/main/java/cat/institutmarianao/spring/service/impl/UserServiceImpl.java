package cat.institutmarianao.spring.service.impl;

import cat.institutmarianao.spring.model.User;
import cat.institutmarianao.spring.repository.UserRepository;
import cat.institutmarianao.spring.service.UserService;

public class UserServiceImpl implements UserService {
	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
