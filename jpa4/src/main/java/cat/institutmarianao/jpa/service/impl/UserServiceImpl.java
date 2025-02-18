package cat.institutmarianao.jpa.service.impl;

import cat.institutmarianao.jpa.model.User;
import cat.institutmarianao.jpa.service.UserService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserServiceImpl implements UserService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void create(User user) {
		entityManager.persist(user);
	}

	@Override
	public void edit(User user) {
		entityManager.merge(user);
	}

	@Override
	public void remove(User user) {
		entityManager.remove(user);
	}

	@Override
	public User findUserByUsername(String username) {
		return (User) entityManager.createQuery("SELECT object(o) FROM User o WHERE o.username = :username")
				.setParameter("username", username).getSingleResult();
	}
}
