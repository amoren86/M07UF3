package cat.institutmarianao.user;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Transactional
@Repository("userHibernateDAO")
public class UserHibernateDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User getById(Integer userId) {
		return getSession().get(User.class, userId);
	}

	@Override
	public void save(User user) {
		getSession().persist(user);
	}

	@Override
	public User edit(User user) {
		return getSession().merge(user);
	}

	@Override
	public void remove(User user) {
		getSession().remove(user);
	}

	@Override
	public User findUserByUsername(String username) {
		CriteriaBuilder criteria = getSession().getCriteriaBuilder();
		CriteriaQuery<User> userQuery = criteria.createQuery(User.class);
		Root<User> userRoot = userQuery.from(User.class);

		userQuery.select(userRoot).where(criteria.equal(userRoot.get("username"), username));
		try {
			return getSession().createQuery(userQuery).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findUserWithHighestRank() {
		CriteriaBuilder criteria = getSession().getCriteriaBuilder();
		CriteriaQuery<User> userQuery = criteria.createQuery(User.class);
		Root<User> userRoot = userQuery.from(User.class);

		userQuery.select(userRoot).orderBy(criteria.desc(userRoot.get("rank")));

		List<User> users = getSession().createQuery(userQuery).getResultList();
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public List<User> findActiveUsers() {
		CriteriaBuilder criteria = getSession().getCriteriaBuilder();
		CriteriaQuery<User> userQuery = criteria.createQuery(User.class);
		Root<User> userRoot = userQuery.from(User.class);

		userQuery.select(userRoot).where(criteria.equal(userRoot.get("active"), true));
		return getSession().createQuery(userQuery).getResultList();
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}