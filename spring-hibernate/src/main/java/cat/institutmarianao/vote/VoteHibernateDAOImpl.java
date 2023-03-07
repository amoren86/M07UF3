package cat.institutmarianao.vote;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Transactional
@Repository("VoteHibernateDAO")
public class VoteHibernateDAOImpl implements VoteDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Vote getById(Integer voteId) {
		return getSession().get(Vote.class, voteId);
	}

	@Override
	public void save(Vote vote) {
		getSession().saveOrUpdate(vote);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
