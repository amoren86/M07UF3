package cat.institutmarianao.user;

import java.util.ArrayList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserJPADAO implements UserDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public User getById(Integer id) {
		try {
			return (User) entityManager.createQuery("select object(o) from User o " + "where o.id = :id")
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void save(User user) {
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
	public User findUserByUsername(String username) {
		try {
			return (User) entityManager.createQuery("select object(o) from User o " + "where o.username = :username")
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findActiveUsers() {
		try {
			return entityManager.createQuery("select object(o) from User o " + "where o.active= true").getResultList();
		} catch (NoResultException e) {
			return new ArrayList<>();
		}
	}

	@Override
	public User findUserWithHighestRank() {
		try {
			return (User) entityManager.createQuery("select object(o) from User o order by o.rank DESC")
					.setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}