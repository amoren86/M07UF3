package cat.institutmarianao.vote;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Transactional
@Repository("voteHibernateDAO")
public class VoteHibernateDaoImpl implements VoteDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Vote getById(Integer voteId) {
		return getSession().get(Vote.class, voteId);
	}

	@Override
	public void save(Vote vote) {
		getSession().persist(vote);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
