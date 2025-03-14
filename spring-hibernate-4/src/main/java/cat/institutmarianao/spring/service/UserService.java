package cat.institutmarianao.spring.service;

import cat.institutmarianao.spring.model.User;

public interface UserService {

	User findByUsername(String username);

}