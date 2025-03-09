package cat.institutmarianao.spring.repository.impl;

import java.util.List;

import cat.institutmarianao.spring.model.User;
import cat.institutmarianao.spring.repository.UserRepository;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserJpaRepositoryImpl implements UserRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void create(User user) {
		entityManager.persist(user);
	}

	@Override
	public User edit(User user) {
		return entityManager.merge(user);
	}

	@Override
	public void remove(User user) {
		user = entityManager.merge(user);
		entityManager.remove(user);
	}

	@Override
	public User findByUsername(String username) {
		try {
			return entityManager.createQuery("select object(o) from User o where o.username = :username", User.class)
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<User> findActive() {
		return entityManager.createQuery("select object(o) from User o where o.active= true", User.class)
				.getResultList();
	}

	@Override
	public User findByHighestRank() {
		try {
			return entityManager.createQuery("select object(o) from User o order by o.rank desc", User.class)
					.setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
